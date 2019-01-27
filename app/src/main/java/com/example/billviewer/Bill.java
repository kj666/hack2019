package com.example.billviewer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Bill {

    private int billID;
    private String name;
    private double total;
    private ArrayList<Item> items;
    private String address;
    private Date date;
    private String phone;
    private String type;


    public Bill(int billI, String title, ArrayList<Item> item){
        billID = billI;
        name = title;
        items = item;
        total = getTotal(item);
    }
    public double getTotal(ArrayList<Item> items){
        double tempTotal = 0;
        for(int i = 0; i <items.size(); i++){
            tempTotal += items.get(i).getPrice();
        }
        return tempTotal;
    }

    public double getFinalTotal(){
        return total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBillTitle() {
        return name;
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setBillID(int id){
        billID = id;
    }

    public int getBillID(){
        return billID;
    }


}
