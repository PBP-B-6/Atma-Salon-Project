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

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    //yohan
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        thread.start();
//        try {
//            Thread.sleep(6000);
//            Intent move = new Intent(this, LoginActivity.class);
//            startActivity(move);
//            finish();
//
//
//        }
//        catch(Exception e)
//        {
//            System.out.println(e.getMessage());
//        }
    }
}