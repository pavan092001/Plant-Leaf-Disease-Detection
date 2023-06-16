package com.example.plant;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plant.ml.Corn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CornPredict extends AppCompatActivity {

    TextView result;
    ImageView imageView;
    Button treatment;
    FloatingActionButton picture;
    FloatingActionButton gallery;
    Bitmap image;
    int imageSize = 224;//default image size

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corn_predict);
        getSupportActionBar().setTitle("Corn");
        result=findViewById(R.id.result);
        imageView=findViewById(R.id.img);
        picture=findViewById(R.id.camera);
        gallery=findViewById(R.id.galary);
        treatment=findViewById(R.id.cure);




        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gallery open

                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent igallery = new Intent(Intent.ACTION_PICK);
                    igallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(igallery, 2);
                } else {
                    //request camera permission if we don't have
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                }
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)

            @Override
            public void onClick(View view) {
                //camera open
                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);

                }
                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);

                }

            }
        });
        treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = result.getText().toString();
                if(name.equalsIgnoreCase("Healthy Corn")){

                    Toast.makeText(CornPredict.this, "Your Plant is Healthy", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(image==null)
                    {
                        Toast.makeText(CornPredict.this, "Please select Image first", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Intent treat= new Intent(getApplicationContext(),Treatment.class);
                        treat.putExtra("type","corn");
                        treat.putExtra("name",name);
                        treat.putExtra("img",image);
                        startActivity(treat);

                    }
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);
            result.setVisibility(View.VISIBLE);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        if(requestCode==2 && resultCode==RESULT_OK){
             image = uriToBitmap(data.getData());
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);
            result.setVisibility(View.VISIBLE);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);

        }
        super.onActivityResult(requestCode, resultCode, data);


    }
    private void classifyImage(Bitmap image) {
        try {

            Corn model = Corn.newInstance(getApplicationContext());
            //create input for reference
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            // get 1D array of 224 224 pixels in image
            int[] intValue = new int[imageSize * imageSize];
            image.getPixels(intValue, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            // iterate over pixels and extract R, G,B values, add to bytebuffer
            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValue[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);
            //run model interface and gets result
            Corn.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeatures0 = outputs.getOutputFeature0AsTensorBuffer();


            float[] confidence = outputFeatures0.getFloatArray();
            // find the index of the class with the biggest confidence
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidence.length; i++) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Corn Blight", "Common Rust","Gray Leaf Spot","Healthy Corn"};
            result.setText(classes[maxPos]);
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // to search the disease on internet
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/search?q=" + result.getText())));
                }
            });
            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Bitmap uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


}