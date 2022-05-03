package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mystore extends AppCompatActivity {

    String product_name;
    String product_price;

    private FirebaseUser user;
    private DatabaseReference referenceUsers;
    private String userID;

    private DatabaseReference referenceProducts;

    private Users profileUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystore);


        user = FirebaseAuth.getInstance().getCurrentUser();
        referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        referenceProducts = FirebaseDatabase.getInstance().getReference("Products");

        TextView tv_name_disp = (TextView) findViewById(R.id.tv_name_disp);
        TextView tv_store_name_mystore = (TextView) findViewById(R.id.tv_store_name_mystore);

        referenceUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileUser = snapshot.getValue(Users.class);

                tv_name_disp.setText(profileUser.getName());
                tv_store_name_mystore.setText(profileUser.getStoreName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mystore.this,"Please try again!", Toast.LENGTH_SHORT);
            }
        });

    }

    public void TextView (View view) {
        TextView tv = (TextView) view;

        if (tv.getTag().toString().equalsIgnoreCase("profile")){
            startActivity(new Intent(mystore.this, UserProfile.class));
        }
    }

    public void Button(View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("add")){
            //get inputs from user
            product_name = ((EditText) findViewById(R.id.input_product_name)).getText().toString();
            product_price = ((EditText) findViewById(R.id.input_price)).getText().toString();

            if (!product_name.isEmpty() && !product_price.isEmpty()){
                Products product = new Products(userID, product_name, product_price);
                referenceProducts.push().setValue(product);
            }



            //add item to database
        }
    }
}