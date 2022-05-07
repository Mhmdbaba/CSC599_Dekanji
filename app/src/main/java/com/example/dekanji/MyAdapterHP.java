package com.example.dekanji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterHP extends RecyclerView.Adapter<MyAdapterHP.MyViewHolder> {

    Context context;
    ArrayList<Users> list;

    public MyAdapterHP(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.store,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Users user = list.get(position);

        holder.store_name.setText(user.getStoreName());
        holder.store_location.setText(user.getLocation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView store_name, store_location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            store_name = itemView.findViewById(R.id.tv_cardview_storename);
            store_location = itemView.findViewById(R.id.tv_cardview_location);
        }
    }
}
