package com.example.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Treatment extends AppCompatActivity {

    String name,type;
    TextView res;
    TextView name_diseases;
    TextView cause;
    Bitmap img;
    ImageView resimg;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        getSupportActionBar().setTitle("Treatment");
        Intent i = getIntent();
        name=i.getStringExtra("name");
        type = i.getStringExtra("type");
        img = (Bitmap) i.getParcelableExtra("img");
        res=findViewById(R.id.treat);
        name_diseases=findViewById(R.id.name);
        cause=findViewById(R.id.cause);
        resimg= findViewById(R.id.img);
        resimg.setImageBitmap(img);
        fetchData(name,type);
    }
    private void fetchData(String name,String type)
    {
        reference = FirebaseDatabase.getInstance().getReference(type);
        reference.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task){

                if(task.isSuccessful())
                {
                    System.out.println("task");
                   if(task.getResult().exists())
                   {

                       DataSnapshot dataSnapshot=task.getResult();
                       String name = String.valueOf(dataSnapshot.child("name").getValue());
                       String ca = String.valueOf(dataSnapshot.child("cause").getValue());
                       String treatment=String.valueOf(dataSnapshot.child("Treatment").getValue());
                       name_diseases.setText(name);
                       cause.setText(ca);
                       res.setText(treatment);
                   }
                   else
                   {
                       Toast.makeText(Treatment.this, "Healthy plant", Toast.LENGTH_SHORT).show();
                   }
                }else
                {
                    Toast.makeText(Treatment.this, "Unable tp fetch data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}