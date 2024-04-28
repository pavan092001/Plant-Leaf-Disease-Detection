package com.example.plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseUtil.isLoggedIn() || check_skip()) {
                    startActivity(new Intent(Splash.this, Home.class));
                    finish();
                }
                else {
                    startActivity(new Intent(Splash.this, Login.class));
                    finish();
                }
            }
        },1000);

    }




    public boolean check_skip()
    {
        SharedPreferences s =getSharedPreferences("app",MODE_PRIVATE);
         return s.getBoolean("skip",false);
    }
}