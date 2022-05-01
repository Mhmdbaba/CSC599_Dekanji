package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;


    String reg_name;
    String reg_email;
    String reg_password;
    String reg_conf_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

    }

    public void Button (View view) {
        Button btn = (Button) view;

        //when the user presses the back button
        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            Intent intent = new Intent(this, type.class);
            startActivity(intent);
        }
        //register button (submit)
        if (btn.getTag().toString().equalsIgnoreCase("register")){
            //gets variables and puts them in strings
            reg_name = ((EditText) findViewById(R.id.input_reg_name)).getText().toString().trim();
            reg_email = ((EditText) findViewById(R.id.input_reg_email)).getText().toString().trim();
            reg_password = ((EditText) findViewById(R.id.input_reg_password)).getText().toString().trim();
            reg_conf_password = ((EditText) findViewById(R.id.input_reg_conf_password)).getText().toString().trim();

            //checks if fields are empty
            if (!reg_name.isEmpty() && !reg_email.isEmpty() && !reg_password.isEmpty()
                    && !reg_conf_password.isEmpty()){
                //check if passwords match
                if (reg_password.equals(reg_conf_password)){
                    //save to database
                    mAuth.createUserWithEmailAndPassword(reg_email,reg_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Users user = new Users(reg_name,reg_email,reg_password,0);

                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                    Log.i("Registration: ", "done");

                                                    //redirect tp login page
                                                    startActivity(new Intent(Register.this, MainActivity.class));
                                                }
                                                else { //if the email already exists
                                                    Toast.makeText(Register.this, "Failed to register! Please try again.1", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else {
                                        Toast.makeText(Register.this, "Failed to register! Please try again.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
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