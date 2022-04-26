package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class types extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);
    }

    public void Button (View view){
        Button btn = (Button) view;
        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (btn.getTag().toString().equalsIgnoreCase("user")){
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
        if (btn.getTag().toString().equalsIgnoreCase("store")){
            Intent intent = new Intent(this,StoreOwner.class);
            startActivity(intent);
        }

    }
}