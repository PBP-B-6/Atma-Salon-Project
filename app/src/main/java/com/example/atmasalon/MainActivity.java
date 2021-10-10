package com.example.atmasalon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(6000);
            Intent move = new Intent(this, LoginActivity.class);
            startActivity(move);
            finish();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}