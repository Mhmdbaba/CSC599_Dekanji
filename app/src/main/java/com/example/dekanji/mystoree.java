package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class mystoree extends AppCompatActivity {

    String product_name;
    String product_price;

    private FirebaseUser user;
    private DatabaseReference referenceUsers;
    private String userID;

    private DatabaseReference referenceProducts;

    private Users profileUser;

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Products> list;

    EditText input_product_name;
    EditText input_price;
    Button btn_add_prod;

    ImageView storeOwner_profileImg;

    Products prod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystoree);


        user = FirebaseAuth.getInstance().getCurrentUser();
        referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        referenceProducts = FirebaseDatabase.getInstance().getReference("Products");

        TextView tv_name_disp = (TextView) findViewById(R.id.tv_name_disp);
        TextView tv_store_name_mystore = (TextView) findViewById(R.id.tv_store_name_mystore);
        storeOwner_profileImg = findViewById(R.id.storeOwner_profileImg);

        recyclerView = findViewById(R.id.product_list);

        input_product_name = (EditText) findViewById(R.id.input_product_name);
        input_price = (EditText) findViewById(R.id.input_price);
        btn_add_prod = (Button) findViewById(R.id.btn_add_product);


        if (getIntent() != null && getIntent().getIntExtra("EDIT", 0) != 0){
            int edit_prodID = getIntent().getIntExtra("EDIT", 0);
//            referenceProducts.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                prod = dataSnapshot.getValue(Products.class);
//                                if (prod.getProductID() == (edit_prodID)){
//                                    break;
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });

            Toast.makeText(this, String.valueOf(edit_prodID), Toast.LENGTH_SHORT).show();
        }

//        prod = (Products) getIntent().getParcelableExtra("EDIT");
//        Toast.makeText(this, prod.getProductID(), Toast.LENGTH_SHORT).show();


//
//        if (prod == null) {
//            btn_add_prod.setText("Add");
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//        else {
//
//            input_price.setText(prod.getPrice().toString());
//            input_product_name.setText(prod.getProductName().toString());
//            btn_add_prod.setText("Update");
//            recyclerView.setVisibility(View.GONE);
//        }

        referenceUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileUser = snapshot.getValue(Users.class);

                tv_name_disp.setText(profileUser.getName());
                tv_store_name_mystore.setText(profileUser.getStoreName());

                Uri imgUri = Uri.parse(profileUser.getmImageUrl());
                Picasso.with(mystoree.this).load(imgUri).into(storeOwner_profileImg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mystoree.this,"Please try again!", Toast.LENGTH_SHORT);
            }
        });

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
                    if (product.getUserID().equals(userID)){
                        list.add(product);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mystoree.this,"Please try again!", Toast.LENGTH_SHORT);
            }
        });


    }

    public void TextView (View view) {
        TextView tv = (TextView) view;

        if (tv.getTag().toString().equalsIgnoreCase("profile")){
            finish();
            startActivity(new Intent(mystoree.this, UserProfile.class));
        }
    }

    public void Button(View view){
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("add")){

            //get inputs from user
            product_name = ((EditText) findViewById(R.id.input_product_name)).getText().toString();
            product_price = ((EditText) findViewById(R.id.input_price)).getText().toString();

            if (!product_name.isEmpty() && !product_price.isEmpty()){

//                if (prod == null) {
                //add item to database
                Products product = new Products(userID, product_name, product_price);
                referenceProducts.push().setValue(product);
//                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(mystoree.this, mystoree.class));
//                }
//                if (prod != null) {
//                    int edit_prod_id = prod.getProductID();
//                    final String[] edit_key = new String[1];
//
//                    referenceProducts.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                                Products product = dataSnapshot.getValue(Products.class);
//                                if (product.getProductID() == (edit_prod_id)){
//                                    edit_key[0] = dataSnapshot.getKey();
//                                    break;
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                    HashMap<String, Object> hashMap = new HashMap<>();
//                    hashMap.put("productName",input_product_name.toString());
//                    hashMap.put("price",input_price.toString());
//                    referenceProducts.child(edit_key.toString()).updateChildren(hashMap);
//                }

            }
        }

        if (btn.getTag().toString().equalsIgnoreCase("orders")) {
            startActivity(new Intent(mystoree.this, Orders.class));
        }
    }
}