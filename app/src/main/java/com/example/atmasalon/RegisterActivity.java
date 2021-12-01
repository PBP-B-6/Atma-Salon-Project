package com.example.atmasalon;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atmasalon.api.UserApi;

import com.example.atmasalon.databinding.ActivityRegisterBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.entity.UserFromJson;
import com.example.atmasalon.entity.UserResponse;
import com.example.atmasalon.unitTesting.ActivityUtil;
import com.example.atmasalon.unitTesting.UserPresenter;
import com.example.atmasalon.unitTesting.UserView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, UserView {
    private ActivityRegisterBinding binding;
    private User user;
    private RequestQueue queue;

//    private UserPresenter presenter;

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
                CreateUser();
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
        String regexPhone = "08+[0-9]{8,11}";
        String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(binding.inputLayoutNama.getEditText().getText().toString().isEmpty())
        {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.inputLayoutEmail.getEditText().getText().toString().isEmpty())
        {
            Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!binding.inputLayoutEmail.getEditText().getText().toString().matches(regexEmail))
        {
            Toast.makeText(this, "Email tidak sesuai format", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.inputLayoutPasswordRegister.getEditText().getText().toString().isEmpty())
        {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.inputLayoutRepeatPassword.getEditText().getText().toString().isEmpty())
        {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!binding.inputLayoutPasswordRegister.getEditText().getText().toString().equals(binding.inputLayoutRepeatPassword.getEditText().getText().toString()))
        {
            Toast.makeText(this, "Password harus sesuai", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!binding.radioPria.isChecked() && !binding.radioWanita.isChecked())
        {
            Toast.makeText(this, "Jenis Kelamin harus dipilih", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.inputLayoutPhone.getEditText().getText().toString().isEmpty())
        {
            Toast.makeText(this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.inputLayoutPhone.getEditText().getText().toString().length() < 10 || binding.inputLayoutPhone.getEditText().getText().toString().length() > 13)
        {
            Toast.makeText(this, "Nomor Telepon tidak boleh < 10 dan > 13 digit", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!binding.inputLayoutPhone.getEditText().getText().toString().matches(regexPhone))
        {
            Toast.makeText(this, "Nomor Telepon tidak sesuai format", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.inputLayoutPhone.getEditText().getText().toString().length() < 10 || binding.inputLayoutPhone.getEditText().getText().toString().length() > 13)
        {
            Toast.makeText(this, "Nomor Telepon tidak boleh < 10 dan > 13 digit", Toast.LENGTH_SHORT).show();
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

    private void CreateUser() {
        setLoading(true);
        User data = binding.getUser();

        int kelamin;
        if(GetKelamin())
        {
            kelamin = 1;
        }
        else
        {
            kelamin = 0;
        }

        UserFromJson user = new UserFromJson(data.getNama(), data.getEmail(), data.getPassword(), kelamin, data.getNoTelp(), 0, "default", 0);

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

                        setLoading(false);

                        Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(move);
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

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

//        presenter.onProfilClicked();
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    @Override
    public String getNama() {
        return binding.inputLayoutNama.getEditText().getText().toString();
    }

    @Override
    public void showNamaError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getEmail() {
        return binding.inputLayoutEmail.getEditText().getText().toString();
    }

    @Override
    public void showEmailError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getPassword1() {
        return binding.inputLayoutPasswordRegister.getEditText().getText().toString();
    }

    @Override
    public void showPassword1Error(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getPassword2() {
        return binding.inputLayoutRepeatPassword.getEditText().getText().toString();
    }

    @Override
    public void showPassword2Error(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPasswordMatchingError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getPhone() {
        return binding.inputLayoutPhone.getEditText().getText().toString();
    }

    @Override
    public void showPhoneError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getKelaminValue() {
        if(!binding.radioPria.isChecked() && !binding.radioWanita.isChecked())
        {
            return -1;
        }
        else if(GetKelamin())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public void showKelaminError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainUser() {
        new ActivityUtil(this).startMainUser();
    }

    @Override
    public void showUserError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.layoutLoading.setVisibility(View.GONE);
        }
    }
}