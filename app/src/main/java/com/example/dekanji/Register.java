package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {


    String reg_name;
    String reg_username;
    String reg_email;
    String reg_password;
    String reg_conf_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void Button (View view) {
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            Intent intent = new Intent(this, type.class);
            startActivity(intent);
        }
        else if (btn.getTag().toString().equalsIgnoreCase("register")){
            reg_name = ((EditText) findViewById(R.id.input_reg_name)).getText().toString();
            reg_email = ((EditText) findViewById(R.id.input_reg_email)).getText().toString();
            reg_username = ((EditText) findViewById(R.id.input_reg_username)).getText().toString();
            reg_password = ((EditText) findViewById(R.id.input_reg_password)).getText().toString();
            reg_conf_password = ((EditText) findViewById(R.id.input_reg_conf_password)).getText().toString();

            if (!reg_name.isEmpty() && !reg_username.isEmpty() && !reg_email.isEmpty() && !reg_password.isEmpty()
                    && !reg_conf_password.isEmpty()){
                //check if passwords match
                if (reg_password.equals(reg_conf_password)){
                    //save to database
                    ALTERUser alter = new ALTERUser();

                    User user = new User (reg_name,reg_username, reg_username,reg_password);
                    alter.add(user).addOnSuccessListener(suc-> {
                       Toast.makeText(this, )
                    });
                    //direct to home page

                }
                else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            else {
                Toast.makeText(this, "Enter all Fields", Toast.LENGTH_SHORT).show();
                return;
            }

        }
    }
}