package com.example.dekanji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
        holder.txt_options.setOnClickListener(v->
        {
            PopupMenu popupMenu = new PopupMenu(context, holder.txt_options);
            popupMenu.inflate(R.menu.options_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                if (item.getItemId() == R.id.menu_edit){
                    Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();

                }
                if (item.getItemId() == R.id.menu_remove){
                    Toast.makeText(context, "remove", Toast.LENGTH_SHORT).show();

                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView product_name, price, txt_options;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.tv_product_name);
            price = itemView.findViewById(R.id.tv_product_price);
            txt_options = itemView.findViewById(R.id.txt_options);
        }
    }
}
