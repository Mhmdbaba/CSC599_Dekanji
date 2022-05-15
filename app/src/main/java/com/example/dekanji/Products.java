package com.example.dekanji;

import android.os.Parcelable;

import java.io.Serializable;

public class Products implements Serializable {
    private String userID, productName, price;
    private int productID, active;

    public Products() { }

    public Products(String userID, String productName, String price, int productID) {
        this.userID = userID;
        this.productName = productName;
        this.price = price;
        this.active = 1;
        this.productID = productID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID (String userID){
        this.userID = userID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
