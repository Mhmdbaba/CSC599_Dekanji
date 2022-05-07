package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        int prod = getIntent().getIntExtra("EDIT", 0);
        Toast.makeText(this, String.valueOf(prod), Toast.LENGTH_SHORT).show();
    }
}