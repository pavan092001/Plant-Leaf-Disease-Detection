package com.example.plant;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.example.plant.databinding.ActivityWelcomeBinding;
import java.util.Locale;

public class Welcome extends AppCompatActivity {

    ActivityWelcomeBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadLocal();
        binding.english.setOnClickListener(view -> {
            changeLanguage("en");

        });

        binding.Bangla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("bn");
            }
        });
        binding.marathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("mr");
            }
        });

        binding.hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("hi");
            }
        });
        binding.telugu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("te");
            }
        });


    }
    public void changeLanguage(String s)
    {
        Locale l = new Locale(s);
        Locale.setDefault(l);
        Configuration configuration = new Configuration();
        configuration.locale=l;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        setLan(s);
    }
    public void setLan(String l)
    {
        SharedPreferences s =getSharedPreferences("app",MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putString("lan",l);
        editor.apply();
        startActivity(new Intent(this, Home.class));
        finish();
    }
    public void loadLocal()
    {
        SharedPreferences s =getSharedPreferences("app",MODE_PRIVATE);
        String lan = s.getString("lan","");
        System.out.println(lan);
        if(!lan.equals(""))
            changeLanguage(lan);
    }

}