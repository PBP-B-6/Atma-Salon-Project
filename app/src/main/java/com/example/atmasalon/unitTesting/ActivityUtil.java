package com.example.atmasalon.unitTesting;

import android.content.Context;
import android.content.Intent;

import com.example.atmasalon.RegisterActivity;

public class ActivityUtil
{
    private Context context;

    public ActivityUtil(Context con)
    {
        context = con;
    }

    public void startMainUser()
    {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }
}
