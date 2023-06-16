package com.example.plant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {



    CardView cotton;
    CardView tomato;
    CardView corn;
    CardView apple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cotton = findViewById(R.id.cotton);
        tomato = findViewById(R.id.tomato);
        corn = findViewById(R.id.corn);
        apple = findViewById(R.id.apple);
        cotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this,cottonpredict.class);
                i.putExtra("name","cotton");
                startActivity(i);
            }
        });
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this,TomatoPredict.class);
                i.putExtra("name","tomato");
                startActivity(i);
            }
        });
        corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this,CornPredict.class);
                startActivity(i);
            }
        });
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this,ApplePredict.class);
                i.putExtra("name","apple");
                startActivity(i);
            }
        });
    }
}