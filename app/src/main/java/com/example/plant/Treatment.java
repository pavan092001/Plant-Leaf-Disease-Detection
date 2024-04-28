package com.example.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
        getSupportActionBar().setTitle(R.string.treatment);
        Intent i = getIntent();
        name=i.getStringExtra("name");
        type = i.getStringExtra("type");
        img = (Bitmap) i.getParcelableExtra("img");
        imguri = i.getParcelableExtra("uri");
        res=findViewById(R.id.treat);
        name_diseases=findViewById(R.id.name);
        cause=findViewById(R.id.cause);
        resimg= findViewById(R.id.img);
        Log.d("type",type);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        resimg.setImageBitmap(img);
        String t1 = (String) res.getText();
        String name_lan=getLanguage();
        if(name_lan.equals("en") || name_lan.isEmpty()){
            name_lan=name;

        }else{
            name_lan=name+" "+name_lan;
        }
        Log.i("pavan",name_lan);

        fetchData(name_lan,type);
        if(FirebaseUtil.isLoggedIn()) {
            user = FirebaseAuth.getInstance();
            databaseReference = firebaseDatabase.getReference().child("History").child(user.getUid());
            upload(name, type, ca, img,t1);
        }
    }

    private void upload(String name, String type, String cause, Bitmap img,String t1) {
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
                        newPost.child("img").setValue(task.getResult().toString());
                        newPost.child("treatment").setValue(treatment);
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
                       System.out.println("daf");
                       String name = String.valueOf(dataSnapshot.child("name").getValue());
                       ca = String.valueOf(dataSnapshot.child("cause").getValue());
                       treatment=String.valueOf(dataSnapshot.child("Treatment").getValue());
                       name_diseases.setText(name);
                       cause.setText(ca);
                       res.setText(treatment);
                   }
                   else
                   {
                       Toast.makeText(Treatment.this, "Unable to fetch due to database down", Toast.LENGTH_SHORT).show();
                   }
                }else
                {
                    Toast.makeText(Treatment.this, "Unable tp fetch data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public String getLanguage()
    {
        SharedPreferences s =getSharedPreferences("app",MODE_PRIVATE);
        return s.getString("lan","");
    }
}