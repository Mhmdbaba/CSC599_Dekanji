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

public class mystoree extends AppCompatActivity implements MyAdapterSD.OnNoteListener {

    String product_name;
    String product_price;

    private FirebaseUser user;
    private DatabaseReference referenceUsers;
    private String userID;

    private DatabaseReference referenceProducts;

    private Users profileUser;

    RecyclerView recyclerView;
    MyAdapterSD myAdapterSD;
    ArrayList<Products> list;

    EditText input_product_name;
    EditText input_price;
    Button btn_add_prod, btn_delete_product, btn_mystore_orders;
    ImageView iv_storedisplay_back;


    ImageView storeOwner_profileImg;

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
        btn_delete_product = (Button) findViewById(R.id.btn_delete_product);
        btn_mystore_orders = (Button) findViewById(R.id.btn_mystore_orders);
        iv_storedisplay_back = (ImageView) findViewById(R.id.iv_storedisplay_back);

        //if the product was pressed from the recycler view to edit or delete
        if (getIntent().getSerializableExtra("EDIT") != null) {
            Products edit_prod = (Products) getIntent().getSerializableExtra("EDIT");
//            Toast.makeText(this, edit_prod.getProductName(), Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            input_product_name.setText(edit_prod.getProductName());
            input_price.setText(edit_prod.getPrice());
            btn_add_prod.setText("update");
            btn_delete_product.setVisibility(View.VISIBLE);
            btn_mystore_orders.setVisibility(View.GONE);
            iv_storedisplay_back.setVisibility(View.VISIBLE);
        }
        else if (getIntent().getSerializableExtra("EDIT") == null) {
            recyclerView.setVisibility(View.VISIBLE);
            input_product_name.setText("");
            input_price.setText("");
            btn_add_prod.setText("Add");
            btn_delete_product.setVisibility(View.INVISIBLE);
            btn_mystore_orders.setVisibility(View.VISIBLE);
            iv_storedisplay_back.setVisibility(View.GONE);
        }


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
        myAdapterSD = new MyAdapterSD(this, list, this);
        recyclerView.setAdapter(myAdapterSD);

        referenceProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Products product = dataSnapshot.getValue(Products.class);
                    if (product.getUserID().equals(userID) && product.getActive() == 1){
                        list.add(product);
                    }
                }
                myAdapterSD.notifyDataSetChanged();
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

            if (getIntent().getSerializableExtra("EDIT") != null){
                referenceProducts.child(((Products)getIntent().getSerializableExtra("EDIT")).getProductID()).child("productName").setValue(input_product_name.getText().toString());
                referenceProducts.child(((Products)getIntent().getSerializableExtra("EDIT")).getProductID()).child("price").setValue(input_price.getText().toString());

                //restart activity
                startActivity(new Intent(mystoree.this, mystoree.class));
                finish();
                //when i added the key as a productID it was easier to update the values in the database. The method below is not required anymore
//                referenceProducts.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            Products temp = dataSnapshot.getValue(Products.class);
//                            if (temp.getProductID().toString().trim() ==
//                                    ((Products)getIntent().getSerializableExtra("EDIT")).getProductID().toString().trim()) {
//                                Toast.makeText(mystoree.this, "hello", Toast.LENGTH_SHORT).show();
//                                referenceProducts.child(dataSnapshot.getKey().toString().trim()).child("productName").setValue(input_product_name.getText().toString());
//                                referenceProducts.child(dataSnapshot.getKey().toString().trim()).child("price").setValue(input_price.getText().toString());
////                                Toast.makeText(mystoree.this, dataSnapshot.getKey().toString().trim(), Toast.LENGTH_SHORT).show();
//                                break;
//                            }
//                        }
//                        startActivity(new Intent(mystoree.this, mystoree.class));
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }

            else {
                //get inputs from user
                product_name = ((EditText) findViewById(R.id.input_product_name)).getText().toString();
                product_price = ((EditText) findViewById(R.id.input_price)).getText().toString();

                if (!product_name.isEmpty() && !product_price.isEmpty()){

                    //add item to database
                    String prodID = referenceProducts.push().getKey();
                    Products product = new Products(userID, product_name, product_price,prodID);
                    referenceProducts.child(prodID).setValue(product);

                    //restart activity
                    finish();
                    startActivity(new Intent(mystoree.this, mystoree.class));

                }
            }
        }

        if (btn.getTag().toString().equalsIgnoreCase("delete")){
            referenceProducts.child(((Products)getIntent().getSerializableExtra("EDIT")).getProductID()).child("active").setValue(0);

            //restart activity
            finish();
            startActivity(new Intent(mystoree.this, mystoree.class));
//            referenceProducts.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Products temp = dataSnapshot.getValue(Products.class);
//                        if (temp.getProductID() == ((Products)getIntent().getSerializableExtra("EDIT")).getProductID()) {
//                            referenceProducts.child(dataSnapshot.getKey()).child("active").setValue(0);
//                            break;
//                        }
//                    }
//                    finish();
//                    startActivity(new Intent(mystoree.this, mystoree.class));
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
        }

        if (btn.getTag().toString().equalsIgnoreCase("orders")) {
            startActivity(new Intent(mystoree.this, Orders.class));
        }
    }

    @Override
    public void onNoteClick(int position) {
        Products prod = list.get(position);

//        Toast.makeText(this, prod.getProductName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mystoree.this, mystoree.class);
        intent.putExtra("EDIT", prod);
        finish();
        startActivity(intent);


    }

    public void ImgButton (View view) {
        ImageView iv = (ImageView) view;

        //when the user presses the back button
        if (iv.getTag().toString().equalsIgnoreCase("back")){
            finish();
            startActivity(new Intent(mystoree.this, mystoree.class));
        }
    }
}