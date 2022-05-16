package com.example.dekanji;

import java.io.Serializable;

public class Users implements Serializable {

    //user credentials
    private String name, email, password, location, mImageUrl, phoneNumber;


    private int storeOwner; //if the user is a normal user then 0 else 1

    private String storeName, description;

    public Users() {
    }

    //for user credentials
    public Users(String name, String email, String phoneNumber, String password, String location, String mImageUrl, int storeOwner) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.location = location;
        this.mImageUrl = mImageUrl;
        this.storeOwner = storeOwner;
    }

    //for store owners credentials
    public Users(String name, String email, String password, int storeOwner, String storeName, String location, String phoneNumber, String description, String mImageUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.storeOwner = storeOwner;
        this.storeName = storeName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.mImageUrl = mImageUrl;
    }

    public String getmImageUrl() { return mImageUrl; }

    public void setmImageUrl(String mImageUrl) { this.mImageUrl = mImageUrl; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStoreOwner() {
        return storeOwner;
    }

    public void setStoreOwner(int storeOwner) {
        this.storeOwner = storeOwner;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
