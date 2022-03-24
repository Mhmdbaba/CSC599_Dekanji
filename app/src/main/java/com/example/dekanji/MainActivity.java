package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Button (View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("register")){
            Intent intent = new Intent(this, register.class);
            startActivity(intent);
        }
        else if (btn.getTag().toString().equalsIgnoreCase("login")){
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }


    }
}