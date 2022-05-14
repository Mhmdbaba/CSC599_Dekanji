package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cart extends AppCompatActivity implements MyAdapterSD.OnNoteListener{

    ArrayList<Products> cart;
    RecyclerView cartView;
    MyAdapterSD myAdapterSD;

    TextView tv_emptyCart;
    Button btn_checkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tv_emptyCart = findViewById(R.id.tv_emptyCart);
        btn_checkout = findViewById(R.id.btn_checkout);

        //get products selected by the buyer
        cart = (ArrayList<Products>) getIntent().getSerializableExtra("productsList");
        if (cart.isEmpty()) {
            tv_emptyCart.setVisibility(View.VISIBLE);
            btn_checkout.setVisibility(View.INVISIBLE);
        }
        else {
            tv_emptyCart.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
        }

//        Toast.makeText(this, cart.get(0).getProductName(), Toast.LENGTH_SHORT).show();

        cartView = (RecyclerView) findViewById(R.id.rv_cartView);
        cartView.setHasFixedSize(true);
        cartView.setLayoutManager(new LinearLayoutManager(this));

        myAdapterSD = new MyAdapterSD(this, cart, this);
        cartView.setAdapter(myAdapterSD);

    }

    public void ImgButton (View view) {
        Button btn = (Button) view;
        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            finish();
            Intent intent = new Intent(Cart.this, StoreDisplay.class);
            intent.putExtra("cart", cart);
            startActivity(intent);
        }
    }

    @Override
    public void onNoteClick(int position) {
        cart.remove(position);
        finish();
        startActivity(getIntent());
    }
}