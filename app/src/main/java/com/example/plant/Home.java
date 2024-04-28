package com.example.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.plant.ml.Rice;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CardView cotton;
    CardView tomato;
    CardView corn;
    CardView apple;
    CardView mango;
    CardView grapes;
    CardView cassav;
    CardView rice;

    CardView potato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cotton = findViewById(R.id.cotton);
        tomato = findViewById(R.id.tomato);
        corn = findViewById(R.id.corn);
        potato = findViewById(R.id.potato);
        cassav = findViewById(R.id.cassava);
        apple = findViewById(R.id.apple);
/*        mango = findViewById(R.id.mango);*/
        rice = findViewById(R.id.rice);
        grapes = findViewById(R.id.grapes);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        Menu menu = navigationView.getMenu();
        if(FirebaseUtil.isLoggedIn())
        {
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_history).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
            menu.findItem(R.id.nav_logout).setVisible(true);

        }
        else{
            menu.findItem(R.id.nav_login).setVisible(true);
            menu.findItem(R.id.nav_history).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(false);

        }
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
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
/*        mango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this,MangoPredict.class);
                i.putExtra("name","Mango");
                startActivity(i);
            }
        });*/

        potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this,PotatoPredict.class);
                i.putExtra("name","Potato");
                startActivity(i);
            }
        });
        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this, RicePredict.class);
                i.putExtra("name","Rice");
                startActivity(i);
            }
        });
        grapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this,GrapesPredict.class);
                i.putExtra("name","Potato");
                startActivity(i);
            }
        });

        cassav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Home.this,CassavaPredict.class);
                i.putExtra("name","Cassava");
                startActivity(i);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.nav_home:
                break;
            case R.id.nav_apple:
                startActivity(new Intent(Home.this,ApplePredict.class));
                break;
            case R.id.nav_corn:
                startActivity(new Intent(Home.this,CornPredict.class));
                break;
            case R.id.nav_cotton:
                startActivity(new Intent(Home.this,cottonpredict.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(Home.this,Login.class));
                break;
            case R.id.nav_history:
                startActivity(new Intent(Home.this, History.class));
                break;
            case R.id.nav_logout:
                FirebaseUtil.logout();
                startActivity(new Intent(Home.this,Login.class));
                finish();
                break;

            case R.id.nav_profile:
                    startActivity(new Intent(Home.this, Profile.class));
                    break;


            case R.id.nav_changelan:
                startActivity(new Intent(Home.this,Welcome.class).putExtra("lan_n",true));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}