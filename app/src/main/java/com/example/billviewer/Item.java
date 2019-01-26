package com.example.billviewer;

import java.util.Random;

public class Item {
    private static int assID = 1;
    private String itemTitle;
    private int itemGrade;

    private Item(String title, int grade){
        itemTitle = title;
        itemGrade = grade;
        assID++;
    }

    static public Item generateRandomAssignment(){
        Random rand = new Random();
        String tempTitle = "Item "+ assID;
        int tempGrade = rand.nextInt(100) +1;

        return new Item(tempTitle, tempGrade);
    }

    public String getItemTitle(){
        return itemTitle;
    }

    public int getItemGrade() {
        return itemGrade;
    }

    public int getAssID() {
        return assID;
    }

    static public void resetAssID(){
        assID = 1;
    }

}
