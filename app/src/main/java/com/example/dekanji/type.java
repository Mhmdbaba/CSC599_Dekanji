package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
    }

    public void Button (View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("user")){
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
        if (btn.getTag().toString().equalsIgnoreCase("store")){
            Intent intent = new Intent(this,StoreOwner.class);
            startActivity(intent);
        }

    }
    public void ImgButton (View view) {
        ImageView iv = (ImageView) view;

        if (iv.getTag().toString().equalsIgnoreCase("back")){
            finish();
            startActivity(new Intent(type.this, MainActivity.class));
        }
    }
}