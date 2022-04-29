package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class mystore extends AppCompatActivity {

    String product_name;
    String product_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystore);
    }

    public void Button(View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("back")){
            Intent intent = new Intent(this, HomePage.class);
            intent.putExtra("type","store owner");
            startActivity(intent);
        }
        if (btn.getTag().toString().equalsIgnoreCase("add")){
            //get inputs from user
            product_name = ((EditText) findViewById(R.id.input_product_name)).getText().toString();
            product_price = ((EditText) findViewById(R.id.input_price)).getText().toString();



            //add item to database
        }
    }
}