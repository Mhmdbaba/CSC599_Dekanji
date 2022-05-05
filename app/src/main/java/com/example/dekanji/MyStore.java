package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class mystore extends AppCompatActivity {

    String product_name;
    String product_price;

    private FirebaseUser user;
    private DatabaseReference referenceUsers;
    private String userID;

    private DatabaseReference referenceProducts;

    private Users profileUser;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Products> list;

    EditText editText_productName;
    EditText editText_productPrice;
    Button btn_add;
    Products product_edit;



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

        recyclerView = findViewById(R.id.product_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        referenceProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Products product = dataSnapshot.getValue(Products.class);
                    if (product.getUserID().equals(userID) && product.getRemoved() != 1){
                        product.setProductID(dataSnapshot.getKey());
                        list.add(product);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mystore.this,"Please try again!", Toast.LENGTH_SHORT);
            }
        });


        btn_add = (Button) findViewById(R.id.btn_add);
        editText_productName = (EditText) findViewById(R.id.input_product_name);
        editText_productPrice = (EditText) findViewById(R.id.input_price);

        product_edit = (Products) getIntent().getSerializableExtra("EDIT");
        if (product_edit != null){
            editText_productPrice.setText(product_edit.getPrice());
            editText_productName.setText(product_edit.getProductName());
            recyclerView.setVisibility(View.INVISIBLE);
            btn_add.setText("Update");

        } else {
            btn_add.setText("ADD");
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    public void TextView (View view) {
        TextView tv = (TextView) view;

        if (tv.getTag().toString().equalsIgnoreCase("profile")){
            startActivity(new Intent(mystore.this, UserProfile.class));
        }
    }

    public void Button(View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("add") && product_edit == null){

            //get inputs from user
            product_name = ((EditText) findViewById(R.id.input_product_name)).getText().toString();
            product_price = ((EditText) findViewById(R.id.input_price)).getText().toString();

            if (!product_name.isEmpty() && !product_price.isEmpty()){

                //add item to database
                Products product = new Products(userID, product_name, product_price);
                referenceProducts.push().setValue(product);
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mystore.this, mystore.class));
            }
        } else if (btn.getTag().toString().equalsIgnoreCase("add") && product_edit != null){
            referenceProducts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Products prod = snapshot.getValue(Products.class);
                    product_edit.setProductName(editText_productName.getText().toString());
                    product_edit.setPrice((editText_productPrice.getText().toString()));
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(mystore.this, "Error! Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}