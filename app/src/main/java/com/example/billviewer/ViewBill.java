package com.example.billviewer;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorLong;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ViewBill extends AppCompatActivity {


    //Generate random Bill
    //I created my own driver function
    static protected ArrayList<Bill> generateBillArr(){
        Random rand = new Random();
        int billNo = rand.nextInt(5);
        ArrayList<Bill> tempBill = new ArrayList<Bill>();
        Bill.resetBillID();
        for(int i = 0; i< billNo; i++){
            Item.resetAssID();
            tempBill.add(Bill.generateRandomBill());
        }
        return tempBill;
    }

    ArrayList<Bill> bill = generateBillArr();
    //boolean to know how grade are displayed true = letter, false = numeric
    boolean letterGradeDisplay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        setup();
    }

    //create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //toggle button in option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //check if letterGrade toggle is activated
        switch(item.getItemId()) {
            case R.id.letter_button:
                if(letterGradeDisplay) {
                    letterGradeDisplay = false;
                    item.setChecked(false);
                }
                else{
                    letterGradeDisplay = true;
                    item.setChecked(true);
                }
                setup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setup(){
        LinearLayout listLayout = (LinearLayout) findViewById(R.id.listViewLinearLayout);
        //remove all views inside the linearlayout
        listLayout.removeAllViews();

        //Check if courses are empty
        if(bill.isEmpty()){
            //Text view for empty message and its customization
            TextView emptyMsg = new TextView(getApplicationContext());
            emptyMsg.setText("There is no bills");
            emptyMsg.setTextColor(Color.RED);
            emptyMsg.setGravity(Gravity.CENTER);
            emptyMsg.setTextSize(25);
            emptyMsg.setPadding(0,30, 0, 0);
            emptyMsg.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            listLayout.addView(emptyMsg);
            listLayout.getLayoutParams().height = 200;
        }
        else{
            //create list view
            ListView listView = new ListView(getApplicationContext());
            //adapter for layout inside listview
            CustomAdapter customAdapter = new CustomAdapter();
            //call custom layout for each element of the listview
            listView.setAdapter(customAdapter);
            //add the listview to the linear layout
            listLayout.addView(listView);
        }
    }

    //convert numerical grades to letter grades
    protected String letterGrade(int grade){
        String letter = "";
        if(grade >= 90)
            letter = "A+";
        else if(grade >= 85 && grade <= 89)
            letter = "A";
        else if(grade >= 80 && grade <= 84)
            letter = "A-";
        else if(grade >= 77 && grade <= 79)
            letter = "B+";
        else if(grade >= 73 && grade <= 76)
            letter = "B";
        else if(grade >= 70 && grade <= 72)
            letter = "B-";
        else if(grade >= 67 && grade <= 69)
            letter = "C+";
        else if(grade >= 63 && grade <= 66)
            letter = "C";
        else if(grade >= 60 && grade <= 62)
            letter = "C-";
        else if(grade >= 57 && grade <= 59)
            letter = "D+";
        else if(grade >= 53 && grade <= 56)
            letter = "D";
        else if(grade >= 50 && grade <= 52)
            letter = "D-";
        else if( grade < 49)
            letter = "F";
        else
            letter = "";

        return letter;
    }


    //Edit element of each row of listView
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            //get size of listView = bill size
            return bill.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_layout, null);
            int layoutHeight = 0;
            TextView textView_course = (TextView) convertView.findViewById(R.id.textView_course);
            TextView textView_avg = (TextView) convertView.findViewById(R.id.textView_avgGrade);

            textView_course.setText(bill.get(position).getBillTitle());
//            getBillTitltextView_course.setTextColor(Color.rgb(41, 163, 163));

            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayoutAss);

            //check if assignments are empty
            if (bill.get(position).getAssignments().isEmpty()) {
                textView_avg.setText("--");
                //Create new text View for message
                TextView emptyMsg = new TextView(getApplicationContext());
                emptyMsg.setText("There is no item");
                emptyMsg.setTextColor(Color.RED);
                emptyMsg.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(emptyMsg);
                linearLayout.getLayoutParams().height = 80;
            }

            else {
                for (int i = 0; i < bill.get(position).getAssignments().size(); i++) {
                    TextView assign = new TextView(getApplicationContext());
                    if(!letterGradeDisplay) {
                        assign.setText(bill.get(position).getAssignments().get(i).getItemTitle() + "                             " + bill.get(position).getAssignments().get(i).getItemGrade());
                        textView_avg.setText(String.valueOf(bill.get(position).getTotal()));
                    }
                    else {
                        assign.setText(bill.get(position).getAssignments().get(i).getItemTitle() + "                              " + letterGrade(bill.get(position).getAssignments().get(i).getItemGrade()));
                        textView_avg.setText(String.valueOf(letterGrade(bill.get(position).getTotal())));
                    }
                    assign.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    //Add the textView to the linear layout
                    linearLayout.addView(assign);
                    //extend linear layout for every textView inserted
                    layoutHeight += 75;
                }
                linearLayout.getLayoutParams().height = layoutHeight;
                linearLayout.getLayoutParams().width = 900;
            }

            return convertView;
        }
    }
}
