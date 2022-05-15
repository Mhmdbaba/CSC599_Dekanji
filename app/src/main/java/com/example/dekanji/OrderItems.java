package com.example.dekanji;

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

import java.util.ArrayList;

public class OrderItems extends AppCompatActivity implements MyAdapterSD.OnNoteListener{

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
        if (btn.getTag().toString().equalsIgnoreCase("add")){

        }
    }

    @Override
    public void onNoteClick(int position) {

    }
}