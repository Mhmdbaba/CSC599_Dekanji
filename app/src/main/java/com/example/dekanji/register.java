package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class register extends AppCompatActivity {

    private RadioGroup rd_grp;
    private RadioButton rd_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rd_grp = (RadioGroup) findViewById(R.id.radioGroup);

    }

    public void Button (View view){
        Button btn = (Button) view;
        rd_btn = (RadioButton) findViewById(rd_grp.getCheckedRadioButtonId());

        if (btn.getTag().toString().equalsIgnoreCase("Back")){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if (btn.getTag().toString().equalsIgnoreCase("submit")){
            if (rd_btn.getText().toString().equalsIgnoreCase("buyer")){
                //if buyer the app will direct the user to home page
                //Intent reg = new Intent(this,Buyer.class);
                //startActivity(reg);
            }
            else if (rd_btn.getText().toString().equalsIgnoreCase("Store owner")){
                //if store owner the app will direct th user to fill more information about his store
                Intent intent = new Intent(this,StoreOwner.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this,"Choose if Buyer or Store Owner!",Toast.LENGTH_SHORT).show();
            }
        }

    }
}