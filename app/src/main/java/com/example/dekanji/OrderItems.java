package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderItems extends AppCompatActivity {

    Order order;

    TextView buyer_name, method, location, phoneNumber, total;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        //get the order pressed from the past activity
         order = (Order) getIntent().getSerializableExtra("order");

         rv = findViewById(R.id.rv_buyer_orders);



//         buyer_name = findViewById(R.id.tv_buyer_name_orders);
//         method = findViewById(R.id.tv_buyer_method_orders);
//         location = findViewById(R.id.tv_buyer_method_orders);
//         phoneNumber = findViewById(R.id.tv_buyer_phonenumber_orders);
//         total = findViewById(R.id.tv_buyer_total_orders);
//
//
//        //set the text of text views
//        buyer_name.setText(order.getNumber());
//        method.setText(order.getOrderMethod());
//        location.setText(order.getLocation());
////        phoneNumber.setText(order.getNumber());
//        total.setText(order.getTotal());

//        Toast.makeText(this, order.getList().get(0).getProductName(), Toast.LENGTH_SHORT).show();
    }

    public void ImgButton (View view) {
        ImageView imgview = (ImageView) view;

        if (imgview.getTag().toString().equalsIgnoreCase("back")) {
            finish();
            startActivity(new Intent(this, Orders.class));
        }
    }
}