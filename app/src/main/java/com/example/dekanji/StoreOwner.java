package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StoreOwner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner);
    }

    public void Button(View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("submit")){
            Intent intent = new Intent(this,HomePage.class);
            startActivity(intent);
        }
        else if( btn.getTag().toString().equalsIgnoreCase(("back"))){
            Intent intent = new Intent(this,register.class);
            startActivity(intent);
        }

    }
}