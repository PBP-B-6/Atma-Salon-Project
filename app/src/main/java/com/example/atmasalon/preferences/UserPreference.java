package com.example.atmasalon.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.atmasalon.entity.User;
import com.example.atmasalon.entity.UserLogin;

public class UserPreference {
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;
    Context con;

    public static final String IS_LOGIN = "IsLogin";
    public static final String KEY_ID = "0";
    public static final String KEY_NAME = "Name";
    public static final String KEY_EMAIL = "Username";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_JENISKELAMIN = "Laki";
    public static final String KEY_NOTELP = "624";
    public static final String KEY_SALDO = "624";
    public static final String KEY_URLGAMBAR = "URL";

    public UserPreference(Context C)
    {
        con = C;
        sharedPreference = C.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public void SetLogin(User user)
    {
        editor.putInt(KEY_ID, user.getId());
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, user.getNama());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putBoolean(KEY_JENISKELAMIN, user.isJenisKelamin());
        editor.putString(KEY_NOTELP, user.getNoTelp());
        editor.putFloat(KEY_SALDO, user.getSaldo());
        editor.putString(KEY_URLGAMBAR, user.getUrlGambar());

        editor.commit();
    }

    public UserLogin GetUserLogin()
    {
        String email, pass;

        email = sharedPreference.getString(KEY_EMAIL, null);
        pass = sharedPreference.getString(KEY_PASSWORD, null);
        return new UserLogin(email, pass);
    }

    public User GetUserNow()
    {
        try
        {
            int id = sharedPreference.getInt(KEY_ID, -1);
            boolean kelamin = sharedPreference.getBoolean(KEY_JENISKELAMIN, false);
            float saldo = sharedPreference.getFloat(KEY_SALDO,0);
            String email, password, nama, notelp, url;
            email = sharedPreference.getString(KEY_EMAIL, null);
            password = sharedPreference.getString(KEY_PASSWORD, null);
            nama = sharedPreference.getString(KEY_NAME, null);
            notelp = sharedPreference.getString(KEY_NOTELP, null);
            url = sharedPreference.getString(KEY_URLGAMBAR, null);

            return new User(id, nama, email, password, kelamin, notelp, saldo, url, true);
        }
        catch (Exception e)
        {
            Log.v("PREFERENCE ERROR:", e.getMessage());
            return null;
        }
    }

    public int GetUserID()
    {
        return sharedPreference.getInt(KEY_ID, -1);
    }

    public String GetNamaUser()
    {
        return sharedPreference.getString(KEY_NAME, null);
    }

    public boolean CheckLogin()
    {
        return sharedPreference.getBoolean(IS_LOGIN, false);
    }

    public void SetURLProfilePic(String url)
    {
        editor.putString(KEY_URLGAMBAR, url);
        editor.commit();
    }

    public String GetURLProfilePic()
    {
        return sharedPreference.getString(KEY_URLGAMBAR, null);
    }

    public void Logout()
    {
        editor.clear();
        editor.commit();
    }
}
