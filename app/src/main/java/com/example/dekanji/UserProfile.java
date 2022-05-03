package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        TextView tv_welcome = (TextView) findViewById(R.id.tv_welcome_message);
        TextView tv_email = (TextView) findViewById(R.id.tv_email_address);
        TextView tv_name = (TextView) findViewById(R.id.tv_full_name);
        TextView tv_store_name = (TextView) findViewById(R.id.tv_store_name);
        TextView tv_phone_number = (TextView) findViewById(R.id.tv_phone_number);
        TextView tv_location = (TextView) findViewById(R.id.tv_location);
        TextView tv_description = (TextView) findViewById(R.id.tv_description);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);

                if (userProfile != null) {
                    String full_name = userProfile.getName();
                    String email = userProfile.getEmail();

                    tv_welcome.setText("Welcome, " + full_name + "!");

                    //display user data
                    tv_name.setText(full_name);
                    tv_email.setText(email);

                    //if the user is a store owner display store information
                    if (userProfile.getStoreOwner() != 0) {
                        String store_name = userProfile.getStoreName();
                        String location = userProfile.getLocation();
                        String description = userProfile.getDescription();
                        String phone_number = userProfile.getPhoneNumber();

                        //display store data
                        tv_store_name.setText(store_name);
                        tv_location.setText(location);
                        tv_description.setText(description);
                        tv_phone_number.setText(phone_number);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something wrong happened! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void LogOut (View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(UserProfile.this, MainActivity.class));
    }




}