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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderItems extends AppCompatActivity implements MyAdapterSD.OnNoteListener{

    Order order;

    TextView buyer_name, method, location, phoneNumber, total;
    RecyclerView rv;

    DatabaseReference referenceOrders;

    Button btn_orderItems_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        referenceOrders = FirebaseDatabase.getInstance().getReference("Orders");

        //get the order pressed from the past activity
         order = (Order) getIntent().getSerializableExtra("order");

         rv = findViewById(R.id.rv_buyer_orders);
         rv.setHasFixedSize(true);
         rv.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Products> pr = order.getList();
        MyAdapterSD myAdapterSD = new MyAdapterSD(this, pr, this);
        rv.setAdapter(myAdapterSD);

         buyer_name = (TextView) findViewById(R.id.tv_buyer_name_orders);
         method = (TextView) findViewById(R.id.tv_buyer_method_orders);
         location = findViewById(R.id.tv_buyer_location_orders);
         phoneNumber = findViewById(R.id.tv_buyer_phonenumber_orders);
         total = findViewById(R.id.tv_buyer_total_orders);

         btn_orderItems_done = findViewById(R.id.btn_orderItems_done);

         if (getIntent().getStringExtra("source")  != null) {
             btn_orderItems_done.setVisibility(View.GONE);
         }
         else {
             btn_orderItems_done.setVisibility(View.VISIBLE);
         }


        //set the text of text views
        buyer_name.setText(order.getName());
        method.setText(order.getOrderMethod());
        location.setText(order.getLocation());
        phoneNumber.setText(order.getNumber());
        total.setText("$" + String.valueOf(order.getTotal()));

//        Toast.makeText(this, order.getList().get(0).getProductName(), Toast.LENGTH_SHORT).show();
    }

    public void ImgButton (View view) {
        ImageView imgview = (ImageView) view;

        if (imgview.getTag().toString().equalsIgnoreCase("back")) {
            finish();
            startActivity(new Intent(this, Orders.class));

        }
    }

    public void Button (View view) {
        Button btn = (Button) view;

        //set the order status to done
        if (btn.getTag().toString().equalsIgnoreCase("done")){

            //set the status to done
            referenceOrders.child(order.getOrderID()).child("status").setValue("done");
            finish();
            startActivity(new Intent(OrderItems.this,Orders.class));

//            referenceOrders.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Order or = dataSnapshot.getValue(Order.class);
////                        Toast.makeText(OrderItems.this, String.valueOf(or.getOrderID()), Toast.LENGTH_SHORT).show();
//                        if (or.getOrderID() == order.getOrderID()) {
//                            referenceOrders.child(dataSnapshot.getKey()).child("status").setValue("done");
//                            finish();
//                            startActivity(new Intent(OrderItems.this,Orders.class));
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
        }
    }

    @Override
    public void onNoteClick(int position) {

    }
}