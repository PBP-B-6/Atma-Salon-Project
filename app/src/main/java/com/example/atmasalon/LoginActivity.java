package com.example.atmasalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.ActivityLoginBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.entity.UserLogin;

public class LoginActivity extends Activity implements View.OnClickListener{

    private ActivityLoginBinding binding;
    private UserLogin loginData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginData = new UserLogin();
        binding.setLoginData(loginData);
        binding.btnLinkDaftar.setOnClickListener(this);
        binding.btnMasuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnMasuk:
                if(CekKosong())
                {
                    String usern = loginData.getUsername();
                    String pass = loginData.getPassword();
                    if(CheckLoginStatus(usern, pass))
                    {
                        //Masukkan data ke user preference (Name, ID, User)
//                        Intent move = new Intent(this, )
//                        startActivity(move);
//                        finish();
                        Toast.makeText(this, "GUUD", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "Nama / Sandi salah!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Silahkan isi semua field", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnLinkDaftar:
                Intent move = new Intent(this, RegisterActivity.class);
                startActivity(move);
                finish();
                break;
        }
    }

    private boolean CekKosong()
    {
        if(binding.inputLayoutUsername.getEditText().getText().toString().isEmpty())
        {
            return false;
        }
        if(binding.inputLayoutPassword.getEditText().getText().toString().isEmpty())
        {
            return false;
        }
        return true;
    }

    private boolean CheckLoginStatus(String usern, String pass)
    {
        return DatabaseUser.GetInstance(getApplicationContext()).GetDatabase().userDao().CheckLogin(usern, pass);
    }

    private String GetName(String usern, String pass)
    {
        return DatabaseUser.GetInstance(getApplicationContext()).GetDatabase().userDao().GetUserName(usern, pass);
    }
}