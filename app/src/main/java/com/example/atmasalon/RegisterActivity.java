package com.example.atmasalon;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atmasalon.api.UserApi;
import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.ActivityRegisterBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.entity.UserResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityRegisterBinding binding;
    private User user;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        queue = Volley.newRequestQueue(this.getApplicationContext());

        user = new User();
        binding.setUser(user);

        binding.btnDaftar.setOnClickListener(this);
        binding.btnLinkMasuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnDaftar)
        {
            if(Validasi())
            {
                User data = binding.getUser();
                data.setSaldo(0);
//                data.setJenisKelamin(GetKelamin());
                data.setUrlGambar("");
                data.setStatus(false);

//                AddUser(data);
                //TODO: diubah yang volley, set URL jadi "", isVerified e 0
                CreateUser();

                Intent move = new Intent(this, LoginActivity.class);
                startActivity(move);
                finish();
            }
        }
        else if(view.getId() == R.id.btnLinkMasuk)
        {
            Intent move = new Intent(this, LoginActivity.class);
            startActivity(move);
            finish();
        }
    }

    private boolean Validasi()
    {
        if(CekKosong())
        {
            String pwd = binding.getUser().getPassword();
            TextInputLayout txtInput = binding.inputLayoutRepeatPassword;
            String pwdRepeat = txtInput.getEditText().getText().toString();

            if(pwd.equals(pwdRepeat))
            {
                return true;
            }
            else
            {
                Toast.makeText(this, "Kata sandi tidak sama!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Silahkan isi semua field", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean CekKosong()
    {
        if(binding.inputLayoutNama.getEditText().getText().toString().isEmpty())
        {
            return false;
        }
        else if(binding.inputLayoutEmail.getEditText().getText().toString().isEmpty())
        {
            return false;
        }
        else if(binding.inputLayoutPasswordRegister.getEditText().getText().toString().isEmpty())
        {
            return false;
        }
        else if(binding.inputLayoutPhone.getEditText().getText().toString().isEmpty())
        {
            return false;
        }
        else if(binding.inputLayoutRepeatPassword.getEditText().getText().toString().isEmpty())
        {
            return false;
        }
        else if(!binding.radioPria.isChecked() && !binding.radioWanita.isChecked())
        {
            return false;
        }
        return true;
    }

    private boolean GetKelamin()
    {
        if(binding.radioGroupKelamin.getCheckedRadioButtonId() == binding.radioPria.getId())
        {
            //pria
            return true;
        }
        else
        {
            return false;
        }
    }

    private void AddUser(User user)
    {

        class AddingUser extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseUser.GetInstance(getApplicationContext()).GetDatabase().userDao().InsertUser(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(RegisterActivity.this, "Berhasil menambah data", Toast.LENGTH_SHORT).show();
            }
        }
        AddingUser Add = new AddingUser();
        Add.execute();
    }

    private void CreateUser() {
        //TODO: Mau ada loading nda?
//        setLoading(true);
        User data = binding.getUser();

        User user = new User(data.getNama(), data.getEmail(), data.getPassword(), GetKelamin(), data.getNoTelp());

        final StringRequest stringRequest = new StringRequest(POST, UserApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        UserResponse userResponse =
                                gson.fromJson(response, UserResponse.class);

                        Toast.makeText(RegisterActivity.this, userResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();

                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);

//                        setLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                setLoading(false);

                try {
                    String responseBody =
                            new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(RegisterActivity.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(user);


                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }
}