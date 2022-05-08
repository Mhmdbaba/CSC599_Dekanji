package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Users prod = (Users) getIntent().getSerializableExtra("EDIT");
        Toast.makeText(this, prod.getName(), Toast.LENGTH_SHORT).show();
    }
}