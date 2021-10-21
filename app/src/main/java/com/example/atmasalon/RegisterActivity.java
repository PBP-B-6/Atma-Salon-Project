package com.example.atmasalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.ActivityRegisterBinding;
import com.example.atmasalon.entity.User;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityRegisterBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

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
                data.setJenisKelamin(GetKelamin());
                AddUser(data);
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

    private String GetKelamin()
    {
        String jawaban;
        if(binding.radioGroupKelamin.getCheckedRadioButtonId() == binding.radioPria.getId())
        {
            jawaban = "Pria";
        }
        else
        {
            jawaban = "Wanita";
        }
        return jawaban;
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
}