package com.example.plant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity implements SelectListner {



    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    DatabaseReference  deldb;
    FirebaseStorage firebaseStorage;
    List<PlantModel> list;
    MyAdapter myAdapter;
    TextView history_bar;

    Button del;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setTitle("History");
        recyclerView = findViewById(R.id.recycler);
        del = findViewById(R.id.delete);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("History").child(FirebaseUtil.currentUserId());
        firebaseStorage = FirebaseStorage.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list= new ArrayList<PlantModel>();
        myAdapter = new MyAdapter(list,History.this,this);
        recyclerView.setAdapter(myAdapter);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deldb= firebaseDatabase.getReference().child("History").child(FirebaseUtil.currentUserId());
                deldb.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(History.this, "History is deleted ", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(History.this, "Failed to deleted History", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PlantModel plantModel = snapshot.getValue(PlantModel.class);
                list.add(plantModel);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @Override
    public void onItemClicked(PlantModel plantModel,String imgUri) {
        Intent i = new Intent(History.this,History2.class);
        i.putExtra("plantModel", (Serializable) plantModel);
        i.putExtra("imgUri",imgUri);
        startActivity(i);
    }
}