package com.example.dekanji;

public class Products {

    private String productID;
    private String userID;
    private String productName;
    private String price;
    private String email;
    private int removed = 0;

    public int getRemoved() {
        return removed;
    }

    public void setRemoved(int removed) {
        this.removed = removed;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }


    public Products() { }

    public Products(String userID, String productName, String price) {
        this.userID = userID;
        this.productName = productName;
        this.price = price;
        this.productID = String.valueOf(Globals.globalProductID);
        Globals.globalProductID++;
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
