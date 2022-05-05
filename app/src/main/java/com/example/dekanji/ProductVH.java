package com.example.dekanji;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ProductVH extends RecyclerView.ViewHolder {

    public TextView txt_name, txt_price, txt_option;

    public ProductVH(@NonNull View itemView) {
        super(itemView);
        txt_name = itemView.findViewById(R.id.tv_product_name);
        txt_price = itemView.findViewById(R.id.tv_product_price);
        txt_option = itemView.findViewById(R.id.txt_option);

    }
}
