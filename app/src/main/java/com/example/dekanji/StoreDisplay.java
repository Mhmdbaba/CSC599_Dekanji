package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreDisplay extends AppCompatActivity implements MyAdapterSD.OnNoteListener {

    Users user;
    DatabaseReference referenceProducts;
    DatabaseReference referenceUsers;

    RecyclerView recyclerView;
    MyAdapterSD myAdapterSD;
    private ArrayList<Products> arrayList;

    String StoreuserKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_display);
        user = (Users) getIntent().getSerializableExtra("EDIT");

        //reference the database
        referenceProducts = FirebaseDatabase.getInstance().getReference("Products");
        referenceUsers = FirebaseDatabase.getInstance().getReference("Users");

        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users userDB = dataSnapshot.getValue(Users.class);
                    if (userDB.getEmail().equals(user.getEmail())) {
                        //get the key of the store owner from the database
//                        userKey = dataSnapshot.child(dataSnapshot.getKey()).getKey();
                        StoreuserKey = dataSnapshot.getKey();
//                        Toast.makeText(StoreDisplay.this, userKey, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Display Store name
        TextView tv_storeName = (TextView) findViewById(R.id.tv_storeDisplay_storename);
        tv_storeName.setText(user.getStoreName());

        recyclerView = (RecyclerView) findViewById(R.id.items_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        myAdapterSD = new MyAdapterSD(this, arrayList, this);
        recyclerView.setAdapter(myAdapterSD);

        referenceProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Products prod = dataSnapshot.getValue(Products.class);
                    if (prod.getUserID().equals(StoreuserKey)) {
//                        Toast.makeText(StoreDisplay.this, StoreuserKey, Toast.LENGTH_SHORT).show();
                        arrayList.add(prod);
                    }
                    myAdapterSD.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public  void ImgButton (View view) {
        ImageView btn = (ImageView) view;

        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            finish();
            startActivity(new Intent(this, HomePage.class));
        }
        if (btn.getTag().toString().equalsIgnoreCase("cart")) {
//            startActivity(this, CartView.class);
        }
    }

    @Override
    public void onNoteClick(int position) { //see what will happen
        Products p = arrayList.get(position);
        Toast.makeText(this, p.getProductName(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, ItemDisplay.class);
//        intent.putExtra("EDIT",p);
//        startActivity(intent);
    }
}