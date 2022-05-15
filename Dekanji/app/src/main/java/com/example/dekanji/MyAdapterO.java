package com.example.dekanji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterO extends RecyclerView.Adapter<MyAdapterO.MyViewHolder> {

    Context context;
    ArrayList<Order> list;

    private OnNoteListener monNoteListener;

    public MyAdapterO (Context context, ArrayList<Order> list, OnNoteListener onNoteListener) {
        this.context = context;
        this.list = list;
        this.monNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order,parent, false);
        return new MyViewHolder(v, monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = list.get(position);

        holder.buyer_name.setText(order.getName());
        holder.buyer_number.setText(order.getNumber());
        holder.buyer_location.setText(order.getLocation());
        holder.buyer_total.setText("$" + order.getTotal());
        holder.method.setText(order.getOrderMethod());
    }

    @Override
    public int getItemCount() { return list.size(); }

    public interface OnNoteListener {
        void onNoteClick (int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView buyer_name, buyer_number, buyer_location, buyer_total, method;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            buyer_name = itemView.findViewById(R.id.tv_order_buyer_name);
            buyer_number = itemView.findViewById(R.id.tv_order_buyer_number);
            buyer_location = itemView.findViewById(R.id.tv_order_buyer_location);
            buyer_total = itemView.findViewById(R.id.tv_order_buyer_total);
            method = itemView.findViewById(R.id.tv_order_method);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

}
