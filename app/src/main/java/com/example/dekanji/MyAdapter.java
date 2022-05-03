package com.example.dekanji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Products> list;

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView product_name, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.tv_product_name);
            price = itemView.findViewById(R.id.tv_product_price);
        }
    }
}
