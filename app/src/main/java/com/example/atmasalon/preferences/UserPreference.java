package com.example.atmasalon.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.atmasalon.entity.User;
import com.example.atmasalon.entity.UserLogin;

public class UserPreference {
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;
    Context con;

    public static final String IS_LOGIN = "IsLogin";
    public static final String KEY_USERNAME = "Username";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_NAME = "Name";
    public static final String KEY_ID = "0";

    public UserPreference(Context C)
    {
        con = C;
        sharedPreference = C.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public void SetLogin(UserLogin User, String nama, int ID)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, User.getUsername());
        editor.putString(KEY_PASSWORD, User.getPassword());
        editor.putString(KEY_NAME, nama);
        editor.putInt(KEY_ID, ID);
        editor.commit();
    }

    public UserLogin GetUserLogin()
    {
        String Usern, Pass;

        Usern = sharedPreference.getString(KEY_USERNAME, null);
        Pass = sharedPreference.getString(KEY_PASSWORD, null);
        return new UserLogin(Usern, Pass);
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

    public void Logout()
    {
        editor.clear();
        editor.commit();
    }
}
