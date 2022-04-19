package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    Button btn_my_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btn_my_store = (Button) findViewById(R.id.btn_my_store);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type.equalsIgnoreCase("store owner")){
            btn_my_store.setVisibility(View.VISIBLE);
        }
    }
}