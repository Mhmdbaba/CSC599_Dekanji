package com.example.dekanji;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MyStore extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView iv_uploaded_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        iv_uploaded_image = findViewById(R.id.iv_uploaded_image);
    }


    public void Button(View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("back")){
            Intent intent = new Intent(this, HomePage.class);
            intent.putExtra("type","store owner");
            startActivity(intent);
        }
        if (btn.getTag().toString().equalsIgnoreCase("add")){
            //add item to database
        }
    }

    public void uploadImage (View view){
        Intent gallery_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult (gallery_intent,RESULT_LOAD_IMAGE);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!= null){
            Uri selected_image = data.getData();
            iv_uploaded_image.setImageURI(selected_image);
        }
    }

}