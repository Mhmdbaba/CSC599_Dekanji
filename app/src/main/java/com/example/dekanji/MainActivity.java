package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    String input_email;
    String input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    public void Button (View view){
        Button btn =(Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("login")){
            //In case the user presses login
            //check the user credentials in database and direct the user to home page
            input_email = ((EditText) findViewById(R.id.input_email)).getText().toString().trim();
            input_password = ((EditText) findViewById(R.id.input_password)).getText().toString();

            if (!input_password.isEmpty() && !input_email.isEmpty()) {
                if (input_email.equals("admin") && input_password.equals("admin123")) {
                    finish();
                    Intent intent = new Intent(this, mystoree.class);
                    startActivity(intent);
                }
                else if (input_email != "admin" && input_password != "admin123"){
                    userLogin();
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
    }

    private void userLogin(){
        String hashed = null;

        //hashing the password
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(input_password.getBytes());

            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();

            for (byte b : digest) {
                sb.append(String.format("%02x",b & 0xff));
            }
            hashed = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //sign in using inputs
        mAuth.signInWithEmailAndPassword(input_email,hashed).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    String userID = user.getUid();

                    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users userProfile = snapshot.getValue(Users.class);
                            if (userProfile.getStoreOwner() == 1){
                                finish();
                                startActivity(new Intent(MainActivity.this, mystoree.class));
                            }
                            else {
                                finish();
                                startActivity(new Intent(MainActivity.this,HomePage.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(MainActivity.this, "Failed to login! Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void textView (View view) {
        TextView tv = (TextView) view;
        if (tv.getTag().toString().equalsIgnoreCase("register")){
            //In case the user doesn't have an account
            Intent intent = new Intent(this, type.class);
            startActivity(intent);
        }
    }
}