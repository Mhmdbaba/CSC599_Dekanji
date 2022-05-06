package com.example.dekanji;

public class Products {
    private String userID, productName, price, email;

    public Products() { }

    public Products(String userID, String productName, String price) {
        this.userID = userID;
        this.productName = productName;
        this.price = price;
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
