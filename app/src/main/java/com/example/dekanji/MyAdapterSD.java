package com.example.dekanji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterSD extends RecyclerView.Adapter<MyAdapterSD.MyViewHolder> {

    Context context;
    ArrayList<Products> list;

    private OnNoteListener monNoteListener;

    public MyAdapterSD(Context context, ArrayList<Products> list, OnNoteListener onNoteListener) {
        this.context = context;
        this.list = list;
        this.monNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return new MyViewHolder(v, monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products products = list.get(position);

        holder.product_name.setText(products.getProductName());
        holder.product_price.setText(products.getPrice());
        holder.txt_options.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() { return list.size(); }

    public interface OnNoteListener {
        void onNoteClick (int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView product_name, product_price, txt_options;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);


            txt_options = itemView.findViewById(R.id.txt_options);
            product_name = itemView.findViewById(R.id.tv_product_name);
            product_price = itemView.findViewById(R.id.tv_product_price);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
}
