package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StoreDisplay extends AppCompatActivity {

    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_display);
        user = (Users) getIntent().getSerializableExtra("EDIT");
        Toast.makeText(this, user.getName(), Toast.LENGTH_SHORT).show();

        //Display Store name
        TextView tv_storeName = (TextView) findViewById(R.id.tv_storeDisplay_storename);
        tv_storeName.setText(user.getStoreName());

    }

    public  void ImgButton (View view) {
        ImageView btn = (ImageView) view;

        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            finish();
            startActivity(new Intent(this, HomePage.class));
        }
    }
}