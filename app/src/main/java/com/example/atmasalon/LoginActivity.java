package com.example.atmasalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.ActivityLoginBinding;
import com.example.atmasalon.entity.UserLogin;
import com.example.atmasalon.preferences.UserPreference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityLoginBinding binding;
    private UserLogin loginData;
    private UserPreference userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

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
                if(CheckLoginStatus(email, pass))
                {
                    int id = GetUserId(email, pass);
                    //TODO: URL dikasi url orang yg login sekarang
                    userPref.SetLogin(loginData, GetName(email, pass), id, "");
                    CheckLogin();
                }
                else
                {
                    Toast.makeText(this, "Email / Sandi salah!", Toast.LENGTH_SHORT).show();
                }
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

    private boolean CheckLoginStatus(String email, String pass)
    {
        return DatabaseUser.GetInstance(getApplicationContext()).GetDatabase().userDao().CheckLogin(email, pass);
    }

    private String GetName(String email, String pass)
    {
        return DatabaseUser.GetInstance(getApplicationContext()).GetDatabase().userDao().GetUserName(email, pass);
    }

    private int GetUserId(String email, String pass)
    {
        return DatabaseUser.GetInstance(getApplicationContext()).GetDatabase().userDao().GetUserId(email, pass);
    }
}