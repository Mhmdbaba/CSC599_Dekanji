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

    RecyclerView recyclerView;
    MyAdapterSD myAdapterSD;
    private ArrayList<Products> list;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_display);
        user = (Users) getIntent().getSerializableExtra("EDIT");
//        Toast.makeText(this, user.getName(), Toast.LENGTH_SHORT).show();

        //Get the User ID
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Display Store name
        TextView tv_storeName = (TextView) findViewById(R.id.tv_storeDisplay_storename);
        tv_storeName.setText(user.getStoreName());

        referenceProducts = FirebaseDatabase.getInstance().getReference("Products");
        recyclerView  =(RecyclerView) findViewById(R.id.items_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapterSD = new MyAdapterSD (this, list, this);
        recyclerView.setAdapter(myAdapterSD);

        //Reference the database
        referenceProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Products products = dataSnapshot.getValue(Products.class);

                    //User id getting is the one of the buyer, get the id by reference database on email
                    Toast.makeText(StoreDisplay.this, userID, Toast.LENGTH_SHORT).show();
                    if (products.getUserID().equals(userID)) {
//                        Toast.makeText(StoreDisplay.this, products.getProductName(), Toast.LENGTH_SHORT).show();

                        list.add(products);
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
    }

    @Override
    public void onNoteClick(int position) { //see what will happen
        Products p = list.get(position);
//        Intent intent = new Intent(this, ItemDisplay.class);
//        intent.putExtra("EDIT",p);
//        startActivity(intent);
    }
}