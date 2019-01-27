package com.example.billviewer;
import java.util.ArrayList;
import java.util.Random;

public class Bill {

    private int billID;
    private String name;
    private double totalPrice;
    private ArrayList<Item> items;

    public Bill(int billI, String title, ArrayList<Item> item, int sum){
        billID = billI;
        name = title;
        items = item;
        totalPrice = sum;
    }

//    static public Bill generateRandomBills(String title){
//        Random rand = new Random();
//        int assignmentNo = rand.nextInt(10);
//        ArrayList<Item> tempAssign = new ArrayList<Item>();
//        int tempSum = 0;
//        double sum = 0;
//        for(int i = 0; i< assignmentNo; i++){
//            tempAssign.add(Item.generateRandomAssignment());
//        }
//        //compute the total of assignments
//        for(int i = 0; i< tempAssign.size(); i++){
//            tempSum += tempAssign.get(i).getPrice();
//        }
//        if (assignmentNo != 0){
//            sum = (tempSum);
//        }
//
//        return new Bill(title, tempAssign, (int)Math.round(sum));
//    }


//    static public Bill generateRandomBill(){
//        Random rand = new Random();
//        int assignmentNo = rand.nextInt(10);
//        ArrayList<Item> tempAssign = new ArrayList<Item>();
//        int tempSum = 0;
//        double sum = 0;
//        for(int i = 0; i< assignmentNo; i++){
//            tempAssign.add(Item.generateRandomAssignment());
//        }
//        //compute the total of assignments
//        for(int i = 0; i< tempAssign.size(); i++){
//            tempSum += tempAssign.get(i).getPrice();
//        }
//        if (assignmentNo != 0){
//            sum = (tempSum);
//        }
//
//        return new Bill("Bill " + billID, tempAssign, (int)Math.round(sum));
//    }

    public String getBillTitle() {
        return name;
    }

    public double getTotal() {
        return totalPrice;
    }

    //Reset the course ID when activities ends

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
