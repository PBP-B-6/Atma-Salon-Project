package com.example.atmasalon;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atmasalon.api.UserApi;

import com.example.atmasalon.databinding.ActivityLoginBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.entity.UserLogin;
import com.example.atmasalon.entity.UserResponse;
import com.example.atmasalon.preferences.UserPreference;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityLoginBinding binding;
    private UserLogin loginData;
    private UserPreference userPref;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        queue = Volley.newRequestQueue(this.getApplicationContext());

        loginData = new UserLogin();
        binding.setLoginData(loginData);
        binding.btnLinkDaftar.setOnClickListener(this);
        binding.btnMasuk.setOnClickListener(this);

        userPref = new UserPreference(LoginActivity.this);

        CheckLogin();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnMasuk)
        {
            if(CekKosong())
            {
                String email = loginData.getEmail();
                String pass = loginData.getPassword();

                Login(new UserLogin(email, pass));
            }
            else
            {
                Toast.makeText(this, "Silahkan isi semua field", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId() == R.id.btnLinkDaftar)
        {
            Intent move = new Intent(this, RegisterActivity.class);
            startActivity(move);
            finish();
        }
    }

    private boolean CekKosong()
    {
        if(binding.inputLayoutUsername.getEditText().getText().toString().isEmpty())
        {
            return false;
        }else if(binding.inputLayoutPassword.getEditText().getText().toString().isEmpty())
        {
            return false;
        }
        return true;
    }

    private void CheckLogin()
    {
        if(userPref.CheckLogin())
        {
            Intent move = new Intent(this, ContainerActivity.class);
            startActivity(move);
            finish();
        }
    }

    private void Login(UserLogin user) {
        //TODO: Mau ada loading nda?
//        setLoading(true);

        final StringRequest stringRequest = new StringRequest(POST, UserApi.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        UserResponse userResponse =
                                gson.fromJson(response, UserResponse.class);

                        User userLogin = userResponse.getUser();

                        if(!userLogin.isStatus()) //blm verif, diganti codenya
                        {
                            Toast.makeText(LoginActivity.this, "Aktifkan akun terlebih dahulu!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, userResponse.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);

                            userPref.SetLogin(user, userLogin.getNama(), userLogin.getUrlGambar(), userLogin.getId());
                            CheckLogin();
                        }


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

                    Toast.makeText(LoginActivity.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(),
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