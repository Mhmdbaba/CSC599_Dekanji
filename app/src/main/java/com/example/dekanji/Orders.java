package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Orders extends AppCompatActivity implements MyAdapterO.OnNoteListener {

    FirebaseUser user;
    String userID;

    DatabaseReference referenceOrders;

    ArrayList<Order> cart;
    RecyclerView cartView;
    MyAdapterO myAdapterO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        referenceOrders = FirebaseDatabase.getInstance().getReference("Orders");


        cartView = (RecyclerView) findViewById(R.id.rv_store_orders);
        cartView.setHasFixedSize(true);
        cartView.setLayoutManager(new LinearLayoutManager(this));

        cart = new ArrayList<>();
        myAdapterO = new MyAdapterO(this, cart,this);
        cartView.setAdapter(myAdapterO);

        //get orders from database and show to store owner
        referenceOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    if (order.getStatus().equalsIgnoreCase("new") && order.getStoreID().trim().equals(userID.trim())) {
//                        Toast.makeText(Orders.this, userID, Toast.LENGTH_SHORT).show();
                        cart.add(order);
//                        Toast.makeText(Orders.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
                    }
                    myAdapterO.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Orders.this, "Failed. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Order ord = cart.get(position);
//        Toast.makeText(this, ord.getOrderMethod(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Orders.this, OrderItems.class);
        intent.putExtra("order",ord);
        startActivity(intent);
    }

    public void ImgButton (View view) {
        ImageView btn = (ImageView) view;

        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            finish();
            startActivity(new Intent(this, mystoree.class));
        }
    }
}