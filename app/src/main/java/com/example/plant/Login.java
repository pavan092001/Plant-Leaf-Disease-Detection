package com.example.plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.plant.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    CountryCodePicker countryCodePicker;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityLoginBinding.inflate(getLayoutInflater());
         View view=binding.getRoot();
        setContentView(view);
        mAuth= FirebaseAuth.getInstance();
        countryCodePicker= findViewById(R.id.login_countrycode);
        countryCodePicker.registerCarrierNumberEditText(binding.loginMobileNumber);

        binding.skipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("app",MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putBoolean("skip",true);
                editor.apply();
                startActivity(new Intent(Login.this, Home.class));
                finish();
            }
        });
        binding.sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!countryCodePicker.isValidFullNumber())
                {
                    binding.loginMobileNumber.setError("Phone no is not Valid");
                    return;
                }else{
                    startActivity(new Intent(Login.this,Login_Otp.class).putExtra("phone", countryCodePicker.getFullNumberWithPlus()));
                }
            }
        });

    }
}