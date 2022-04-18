package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String input_username;
    String input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Button (View view){
        Button btn =(Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("login")){
            //In case the user presses login
            //check the user credentials in database and direct the user to home page
            input_username = ((EditText) findViewById(R.id.input_username)).getText().toString();
            input_password = ((EditText) findViewById(R.id.input_password)).getText().toString();

            if (!input_password.isEmpty() && !input_username.isEmpty()) {
                if (input_username.equals("admin") && input_password.equals("admin123")) {
                    //Intent intent = new Intent(this, HomePage.class);
                    //startActivity(intent);
                }
                else if (input_username != "admin" && input_password != "admin123"){
                    //check if the credentials are found in the database
                    //then direct to home screen
                }
                else {
                    Toast.makeText(this,"Please enter correct username and password", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else {
                Toast.makeText(this,"Please enter username and password", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else if (btn.getTag().toString().equalsIgnoreCase("register")){
            //In case the user doesn't have an account
            //Intent intent = new Intent(this, Register.class);
            //startActivity(intent);
        }
    }
}