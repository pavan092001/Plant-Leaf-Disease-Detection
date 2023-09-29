package com.example.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class Treatment extends AppCompatActivity {

    String name,type;
    TextView res;
    TextView name_diseases;
    TextView cause;
    Bitmap img;
    ImageView resimg;
    String ca;
    String treatment;

    Uri imguri;

    FirebaseAuth user;
    DatabaseReference reference;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        getSupportActionBar().setTitle("Treatment");
        Intent i = getIntent();
        name=i.getStringExtra("name");
        type = i.getStringExtra("type");
        img = (Bitmap) i.getParcelableExtra("img");
        imguri = i.getParcelableExtra("uri");
        res=findViewById(R.id.treat);
        name_diseases=findViewById(R.id.name);
        cause=findViewById(R.id.cause);
        resimg= findViewById(R.id.img);
        user = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = firebaseDatabase.getReference().child("History").child(user.getUid());
        resimg.setImageBitmap(img);
        fetchData(name,type);
        upload(name,type,ca,treatment,img);
    }

    private void upload(String name, String type, String cause, String treatment, Bitmap img) {
        StorageReference filepath= firebaseStorage.getReference().child("History").child(imguri.getLastPathSegment());
        filepath.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloaduri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String t = task.getResult().toString();
                        DatabaseReference newPost=databaseReference.push();
                        newPost.child("nameOfPlant").setValue(type);
                        newPost.child("nameOfDiseases").setValue(name);
                        newPost.child("cause").setValue(ca);
                        newPost.child("treatment").setValue(treatment);
                        newPost.child("img").setValue(task.getResult().toString());
                    }
                });
            }
        });

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
                       ca = String.valueOf(dataSnapshot.child("cause").getValue());
                        treatment=String.valueOf(dataSnapshot.child("Treatment").getValue());
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