package com.example.billviewer;

import java.util.Random;

public class Item {
    private static int itemID = 1;
    private String item;
    private double price;
    private String type;

    public Item(String title, double pri){
        item = title;
        price = pri;
    }

    static public Item generateRandomAssignment(){
        Random rand = new Random();
        String tempTitle = "Item "+ itemID;
        int tempGrade = rand.nextInt(100) +1;

        return new Item(tempTitle, tempGrade);
    }

    public String getItemTitle(){
        return item;
    }

    public double getPrice() {
        return price;
    }

    public int getAssID() {
        return itemID;
    }

    static public void resetAssID(){
        itemID = 1;
    }

    public void setItemID(int id){
        itemID = id;
    }
}
