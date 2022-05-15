package com.example.dekanji;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    int OrderID, Total;
    String name, number, location, OrderMethod, status, storeID;
    ArrayList<Products> list;

    public Order (){}

    public Order(String orderMethod, String name, String number, String location, ArrayList<Products> list, int total, String storeID) {
        this.OrderID = Global.globalOrderID;
        this.name = name;
        this.number = number;
        this.location = location;
        OrderMethod = orderMethod;
        this.list = list;
        Total = total;
        this.status = "new";
        this.storeID = storeID;
        Global.globalOrderID++;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderMethod() {
        return OrderMethod;
    }

    public void setOrderMethod(String orderMethod) {
        OrderMethod = orderMethod;
    }

    public ArrayList<Products> getList() {
        return list;
    }

    public void setList(ArrayList<Products> list) {
        this.list = list;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
