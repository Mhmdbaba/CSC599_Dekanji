package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Cart extends AppCompatActivity implements MyAdapterSD.OnNoteListener{

    FirebaseUser user;
    private String userID;
    DatabaseReference referenceUsers;
    DatabaseReference referenceOrders;

    Users profileUser;

    ArrayList<Products> cart;
    RecyclerView cartView;
    MyAdapterSD myAdapterSD;

    TextView tv_emptyCart, tv_total;
    Button btn_checkout;
    RadioGroup rg_method;
    RadioButton checked_rb;

    int total;
    String name, number, location;

    Users StoreUser;
    String StoreNumber;
    String StoreKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        referenceOrders = FirebaseDatabase.getInstance().getReference("Orders");

        StoreKey = getIntent().getStringExtra("StoreKey");

        referenceUsers.child(StoreKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StoreUser = snapshot.getValue(Users.class);
                StoreNumber = StoreUser.getPhoneNumber();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileUser = snapshot.getValue((Users.class));
                name = profileUser.getName();
                number = profileUser.getPhoneNumber();
//                Toast.makeText(Cart.this, name, Toast.LENGTH_SHORT).show();
                location = profileUser.getLocation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Cart.this,"Please try again!", Toast.LENGTH_SHORT);
            }
        });

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
            total = 0;
            for (int i = 0; i < cart.size(); i++) {
                total += Integer.parseInt(cart.get(i).getPrice());
            }

            tv_total.setText("Total: $" +total);
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


            String orderID = referenceOrders.push().getKey();

            Order order = new Order(orderMethod, name, number, location, cart, total, StoreKey, orderID);

            referenceOrders.child(orderID).setValue(order);

            Toast.makeText(this, "Your order has been recorded", Toast.LENGTH_SHORT).show();

            finish();
            startActivity(new Intent(Cart.this, StoreDisplay.class));
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