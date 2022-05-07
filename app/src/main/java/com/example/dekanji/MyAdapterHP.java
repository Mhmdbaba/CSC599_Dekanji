package com.example.dekanji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterHP extends RecyclerView.Adapter<MyAdapterHP.MyViewHolder> {

    Context context;
    ArrayList<Users> list;

    private OnNoteListener monNoteListener;

    public MyAdapterHP(Context context, ArrayList<Users> list, OnNoteListener onNoteListener) {
        this.context = context;
        this.list = list;
        this.monNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.store,parent,false);
        return new MyViewHolder(v, monNoteListener);
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

    public interface OnNoteListener {
        void onNoteClick (int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView store_name, store_location;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            store_name = itemView.findViewById(R.id.tv_cardview_storename);
            store_location = itemView.findViewById(R.id.tv_cardview_location);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
}
