package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cart extends AppCompatActivity implements MyAdapterSD.OnNoteListener{

    ArrayList<Products> cart;
    RecyclerView cartView;
    MyAdapterSD myAdapterSD;

    TextView tv_emptyCart, tv_total;
    Button btn_checkout;
    RadioGroup rg_method;
    RadioButton checked_rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tv_emptyCart = findViewById(R.id.tv_emptyCart);
        btn_checkout = findViewById(R.id.btn_checkout);
        rg_method = findViewById(R.id.rg_orderMethod);
        tv_total = findViewById(R.id.tv_total);


        //get products selected by the buyer
        cart = (ArrayList<Products>) getIntent().getSerializableExtra("productsList");
        if (cart.isEmpty()) {
            tv_emptyCart.setVisibility(View.VISIBLE);
            btn_checkout.setVisibility(View.INVISIBLE);
            rg_method.setVisibility(View.GONE);
        }
        else {
            tv_emptyCart.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            rg_method.setVisibility(View.VISIBLE);
            int t = 0;
            for (int i = 0; i < cart.size(); i++) {
                t += Integer.parseInt(cart.get(i).getPrice());
            }

            tv_total.setText("Total: $" +t);
        }

//        Toast.makeText(this, cart.get(0).getProductName(), Toast.LENGTH_SHORT).show();

        cartView = (RecyclerView) findViewById(R.id.rv_cartView);
        cartView.setHasFixedSize(true);
        cartView.setLayoutManager(new LinearLayoutManager(this));

        myAdapterSD = new MyAdapterSD(this, cart, this);
        cartView.setAdapter(myAdapterSD);

    }

    public void Button (View view) {
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("checkout")){
            checked_rb = (RadioButton) findViewById(rg_method.getCheckedRadioButtonId());

            String orderMethod = checked_rb.getText().toString();
        }
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