package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StoreOwner extends AppCompatActivity {

    String store_name;
    String longtude;
    String latitude;
    String store_number;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner);
        store_name = ((EditText) findViewById(R.id.input_store_name)).getText().toString();
        //location = ((EditText) findViewById(R.id.input_location)).getText().toString();
        store_number = ((EditText) findViewById(R.id.input_number)).getText().toString();
        description = ((EditText) findViewById(R.id.input_description)).getText().toString();
    }

    public void Button(View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
        if (btn.getTag().toString().equalsIgnoreCase("submit")){
            if(!store_name.isEmpty() && !longtude.isEmpty() && !store_number.isEmpty() && !description.isEmpty()){
                //save to database
                //direct to store owner page
            }
            else {
                Toast.makeText(this, "Enter required Fields", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

}