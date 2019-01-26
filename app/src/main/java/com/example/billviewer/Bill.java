package com.example.billviewer;
import java.util.ArrayList;
import java.util.Random;

public class Bill {

    private static int courseID = 1;
    private String courseTitle;
    private int total;
    private ArrayList<Item> items;

    private Bill(String title, ArrayList<Item> assign, int sum){
        courseTitle = title;
        items = assign;
        total = sum;
        courseID++;
    }

    static public Bill generateRandomBill(){
        Random rand = new Random();
        int assignmentNo = rand.nextInt(10);
        ArrayList<Item> tempAssign = new ArrayList<Item>();
        int tempSum = 0;
        double sum = 0;
        for(int i = 0; i< assignmentNo; i++){
            tempAssign.add(Item.generateRandomAssignment());
        }
        //compute the total of assignments
        for(int i = 0; i< tempAssign.size(); i++){
            tempSum += tempAssign.get(i).getItemGrade();
        }
        if (assignmentNo != 0){
            sum = (tempSum);
        }

        return new Bill("Bill " + courseID, tempAssign, (int)Math.round(sum));
    }

    public String getBillTitle() {
        return courseTitle;
    }

    public int getTotal() {
        return total;
    }

    //Reset the course ID when activities ends
    static public void resetBillID(){
        courseID = 1;
    }

    public ArrayList<Item> getAssignments() {
        return items;
    }
}
