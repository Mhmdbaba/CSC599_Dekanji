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

import java.security.MessageDigest;

public class StoreOwner extends AppCompatActivity {
    private FirebaseAuth mAuth;

    String reg_storeowner_name;
    String reg_storeowner_email;
    String reg_storeowner_password;
    String reg_storeowner_conf_password;

    String reg_storeowner_storename;
    String reg_storeowner_location;
    String reg_storeowner_phonenumber;
    String reg_storeowner_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner);

        mAuth = FirebaseAuth.getInstance();
    }

    public void Button (View view) {
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("back")){
            Intent intent = new Intent(this, type.class);
            startActivity(intent);
        }
        if (btn.getTag().toString().equalsIgnoreCase("register store owner")){
            //user information
            reg_storeowner_name = ((EditText) findViewById(R.id.input_reg_storeowner_name)).getText().toString();
            reg_storeowner_email = ((EditText) findViewById(R.id.input_storeowner_email)).getText().toString();
            reg_storeowner_password = ((EditText) findViewById(R.id.input_reg_storeowner_password)).getText().toString();
            reg_storeowner_conf_password = ((EditText) findViewById(R.id.input_reg_storeowner_conf_password)).getText().toString();

            //store information
            reg_storeowner_storename = ((EditText) findViewById(R.id.input_reg_storeowner_storename)).getText().toString();
            reg_storeowner_location = ((EditText) findViewById(R.id.input_reg_storeowner_location)).getText().toString();
            reg_storeowner_phonenumber = ((EditText) findViewById(R.id.input_reg_storeowner_phonenumber)).getText().toString();
            reg_storeowner_description = ((EditText) findViewById(R.id.input_reg_storeowner_description)).getText().toString();

            if (!reg_storeowner_name.isEmpty() && !reg_storeowner_email.isEmpty()
            && !reg_storeowner_password.isEmpty() && !reg_storeowner_conf_password.isEmpty() && !reg_storeowner_storename.isEmpty()
            && !reg_storeowner_location.isEmpty() && !reg_storeowner_phonenumber.isEmpty() && !reg_storeowner_description.isEmpty()){
                //check if passwords match
                if (reg_storeowner_password.equals(reg_storeowner_conf_password)){
                    //check if username is found in database and insert into database all credentials
                    mAuth.createUserWithEmailAndPassword(reg_storeowner_email, reg_storeowner_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        StoreOwnerUser storeOwnerUser = new StoreOwnerUser(reg_storeowner_name,reg_storeowner_email,reg_storeowner_password,
                                                reg_storeowner_storename,reg_storeowner_location,reg_storeowner_phonenumber,reg_storeowner_description);

                                        FirebaseDatabase.getInstance().getReference("StoreOwners")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(storeOwnerUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Log.i("Registration: ", "done");

                                                    //redirect tp login page
                                                    startActivity(new Intent(StoreOwner.this, MainActivity.class));
                                                }
                                                else {
                                                    Toast.makeText(StoreOwner.this, "Failed to register! Please try again.", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }
                                        });
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
                Toast.makeText(this, "Enter all required Fields", Toast.LENGTH_SHORT).show();
                return;
            }



        }
    }

}