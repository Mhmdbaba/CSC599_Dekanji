package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    Users userProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //output text views
        final TextView tv_welcome = (TextView) findViewById(R.id.tv_welcome_message);
        final TextView tv_email = (TextView) findViewById(R.id.tv_email_address);
        final TextView tv_name = (TextView) findViewById(R.id.tv_full_name);
        final TextView tv_store_name = (TextView) findViewById(R.id.tv_store_name_mystore);
        final TextView tv_phone_number = (TextView) findViewById(R.id.tv_phone_number);
        final TextView tv_location = (TextView) findViewById(R.id.tv_location);
        final TextView tv_description = (TextView) findViewById(R.id.tv_description);

        //display text views
        final TextView tv_store_name_disp = (TextView) findViewById(R.id.tv_store_name_disp);
        final TextView tv_phone_number_disp = (TextView) findViewById(R.id.tv_phone_number_disp);
        final TextView tv_location_disp = (TextView) findViewById(R.id.tv_location_disp);
        final TextView tv_description_disp = (TextView) findViewById(R.id.tv_description_disp);



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(Users.class);

                if (userProfile != null) {
                    String full_name = userProfile.getName();
                    String email = userProfile.getEmail();
                    String location = userProfile.getLocation();


                    tv_welcome.setText("Welcome, " + full_name + "!");

                    //display user data
                    tv_name.setText(full_name);
                    tv_email.setText(email);

                    tv_location.setVisibility(View.VISIBLE);
                    tv_location.setText(location);
                    tv_location_disp.setVisibility(View.VISIBLE);

                    //if the user is a store owner display store information
                    if (userProfile.getStoreOwner() == 1) {
                        String store_name = userProfile.getStoreName();
                        String description = userProfile.getDescription();
                        String phone_number = userProfile.getPhoneNumber();

                        tv_store_name.setVisibility(View.VISIBLE);
                        tv_email.setVisibility(View.VISIBLE);
                        tv_phone_number.setVisibility(View.VISIBLE);
                        tv_description.setVisibility(View.VISIBLE);

                        //display store data
                        tv_store_name.setText(store_name);
                        tv_description.setText(description);
                        tv_phone_number.setText(phone_number);

                        //display store owners text views
                        tv_store_name_disp.setVisibility(View.VISIBLE);
                        tv_phone_number_disp.setVisibility(View.VISIBLE);
                        tv_description_disp.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something wrong happened! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void ImgButton (View view) {
        ImageView iv = (ImageView) view;

        if (iv.getTag().toString().equalsIgnoreCase("back")){
            finish();
            if (userProfile.getStoreOwner() == 1) {
                startActivity(new Intent(UserProfile.this, mystoree.class));
            }
            else {
                startActivity(new Intent(UserProfile.this, HomePage.class));
            }
        }
    }

    public void Button (View view) {
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("logout")){
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(UserProfile.this, MainActivity.class));
        }
    }





}