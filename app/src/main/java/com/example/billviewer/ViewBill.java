package com.example.billviewer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorLong;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ViewBill extends AppCompatActivity {

    private CollectionReference billRef = FirebaseFirestore.getInstance().collection("receipt");


    private float x1, x2, y1,y2;

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

    public ArrayList<Bill> bill = new ArrayList<Bill>();


    public void getData(){
        billRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Log.d("receipts", document.getId() + "=>" + document.getData().get("items"));
                                String name = document.getString("name");
                                Map<String,Map<String, String>> sample= (Map<String, Map<String, String>>) document.getData().get("items");
                                //Map<String,Map<String,String>> extract = (Map<String, Map<String, String>>) document.get("items");
                                ArrayList<Item> pass = new ArrayList<Item>();
                                for (String key :sample.keySet()){
                                    String title= sample.get(key).get("item");
                                    Double price =Double.parseDouble(sample.get(key).get("price"));
                                    Item something = new Item(title,price);
                                    pass.add(something);
                                    Log.d("receipts", "heelo");
                                    Log.d("key", title + price);
                                }

                                Bill billO = new Bill(name, pass, 3);
                                bill.add(billO);

                            }
                        }
                        setup();
                    }
                });
    }


    //boolean to know how grade are displayed true = letter, false = numeric
    boolean letterGradeDisplay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        getData();

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

    public boolean onTouchEvent(MotionEvent touchevent){
        switch(touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if(x2>1.5*x1)
                    finish();
                break;
        }
        return false;
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
            //textView_course.setTextColor(Color.rgb(41, 163, 163));

            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayoutAss);

            //check if assignments are empty
            if (bill.get(position).getItems().isEmpty()) {
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
                for (int i = 0; i < bill.get(position).getItems().size(); i++) {
                    TextView assign = new TextView(getApplicationContext());
                    assign.setText(bill.get(position).getItems().get(i).getItemTitle() + "                             " + bill.get(position).getItems().get(i).getPrice());
                    textView_avg.setText(String.valueOf(bill.get(position).getTotal()));

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

            convertView.setOnClickListener(onClickListener);


            return convertView;
        }
//        private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                event.
//                return false;
//            }
//        }
        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSingleBill();
            }
        };

        private void viewSingleBill(){
            Intent intent = new Intent(getApplicationContext(), ViewSingleBill.class);
            startActivity(intent);
        }
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
