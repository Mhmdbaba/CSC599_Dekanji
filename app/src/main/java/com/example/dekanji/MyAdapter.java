package com.example.dekanji;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Products> list;

    private DatabaseReference referenceProducts = FirebaseDatabase.getInstance().getReference("Products");;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();;


    public MyAdapter(Context context, ArrayList<Products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Products products = list.get(position);
        holder.product_name.setText(products.getProductName());
        holder.price.setText(products.getPrice());
        holder.txt_option.setOnClickListener(v->
        {
            PopupMenu popupMenu = new PopupMenu(context, holder.txt_option);
            popupMenu.inflate(R.menu.options_menu);
            popupMenu.setOnMenuItemClickListener(Item ->
            {
                switch (Item.getItemId())
                {
                    case R.id.menu_edit:
                        Intent intent = new Intent(context, mystore.class);
                        intent.putExtra("EDIT", (Serializable) products);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        String prodID = products.getProductID();
                        referenceProducts.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //LEFT HERE CONTINUE TO ITERATE FOR PRODUCT ID TO EDIT
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (prodID == dataSnapshot.getValue(Products.class).getProductID()) {
                                        dataSnapshot.getValue(Products.class).setRemoved(1);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                }
                return false;
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView product_name, price, txt_option;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.tv_product_name);
            price = itemView.findViewById(R.id.tv_product_price);
        }
    }
}
