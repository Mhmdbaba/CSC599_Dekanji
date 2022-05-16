package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History extends AppCompatActivity implements MyAdapterO.OnNoteListener {

    FirebaseUser user;
    String userID;

    DatabaseReference referenceOrders;

    ArrayList<Order> cart;
    RecyclerView cartView;
    MyAdapterO myAdapterO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        referenceOrders = FirebaseDatabase.getInstance().getReference("Orders");

        cartView = (RecyclerView) findViewById(R.id.rv_history);
        cartView.setHasFixedSize(true);
        cartView.setLayoutManager(new LinearLayoutManager(this));

        cart = new ArrayList<>();
        myAdapterO = new MyAdapterO(this, cart,this);
        cartView.setAdapter(myAdapterO);

        referenceOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    if (order.getStoreID().trim().equals(userID.trim())) {
                        cart.add(order);
                    }
                    myAdapterO.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(History.this, "Failed. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {

    }

    public void ImgButton (View view) {
        ImageView iv = (ImageView) view;

        //when the user presses the back button
        if (iv.getTag().toString().equalsIgnoreCase("back")){
            finish();
            startActivity(new Intent(History.this, Orders.class));
        }
    }
}