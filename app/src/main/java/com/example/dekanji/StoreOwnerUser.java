package com.example.dekanji;

public class StoreOwnerUser {
    String name;
    String email;
    String password;
    String store_name;
    String location;
    String phone_number;
    String description;
    private int storeOwner = 1;

    public StoreOwnerUser(String name, String email, String password, String store_name, String location, String phone_number, String description) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.store_name = store_name;
        this.location = location;
        this.phone_number = phone_number;
        this.description = description;
    }

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

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
