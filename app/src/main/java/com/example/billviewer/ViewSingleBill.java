package com.example.billviewer;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ViewSingleBill extends AppCompatActivity {



    String QRCode = MainActivity.QRcode;

    private CollectionReference billRef = FirebaseFirestore.getInstance().collection("receipt");

    public Bill singleBille;
    public ArrayList<Bill> bill = new ArrayList<Bill>();

    public void getData(String id){
        billRef.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Log.d("DATA", "DocumentSnapshot data: " + document.getData());
                        int id = Integer.parseInt(document.getId());
                        String name = document.getString("name");
                        Map<String, Map<String, String>> sample= (Map<String, Map<String, String>>) document.getData().get("items");
                        ArrayList<Item> pass = new ArrayList<Item>();
                        for (String key :sample.keySet()){
                            String title= sample.get(key).get("item");
                            Double price =Double.parseDouble(sample.get(key).get("price"));
                            Item something = new Item(title,price);
                            pass.add(something);
                            Log.d("receipts", "heelo");
                            Log.d("key", title + price);
                        }
                        singleBille = new Bill( id, name,pass);
                    }else{
                        Log.d("ERROR", "No such document");
                    }
                }
                setup();
            }
        });
//        billRef.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot document: task.getResult()){
//                                Log.d("receipts", document.getId() + "=>" + document.getData().get("items"));
//                                String name = document.getString("name");
//                                Map<String, Map<String, String>> sample= (Map<String, Map<String, String>>) document.getData().get("items");
//                                //Map<String,Map<String,String>> extract = (Map<String, Map<String, String>>) document.get("items");
//                                ArrayList<Item> pass = new ArrayList<Item>();
//                                for (String key :sample.keySet()){
//                                    String title= sample.get(key).get("item");
//                                    Double price =Double.parseDouble(sample.get(key).get("price"));
//                                    Item something = new Item(title,price);
//                                    pass.add(something);
//                                    Log.d("receipts", "heelo");
//                                    Log.d("key", title + price);
//                                }
//
//                                Bill billO = new Bill(name, pass, 3);
//                                bill.add(billO);
//
//                            }
//                        }
//                        setup();
//                    }
//                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_bill);
        String id = getIntent().getStringExtra("BillID");

        getData(id);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    protected void setup(){
        RelativeLayout singleBillLaout = (RelativeLayout) findViewById(R.id.RelativeLayoutSingleBill);
        int layoutHeight = 0;
        TextView textView_Store = (TextView) findViewById(R.id.textView_store);
        TextView textView_Total = (TextView) findViewById(R.id.textView_totalSingle);
        textView_Store.setText(singleBille.getBillTitle());

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutAss);
        LinearLayout linearLayoutPrice = (LinearLayout) findViewById(R.id.linearLayoutPrice);


        //check if assignments are empty
        if (singleBille.getItems().isEmpty()) {
            textView_Total.setText("--");
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
            for (int i = 0; i < singleBille.getItems().size(); i++) {
                TextView assign = new TextView(getApplicationContext());
                assign.setText(singleBille.getItems().get(i).getItemTitle());

                TextView price = new TextView(getApplicationContext());
                price.setText(Double.toString(singleBille.getItems().get(i).getPrice()));

                textView_Total.setText(String.valueOf(singleBille.getTotal()));

                assign.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                price.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                //Add the textView to the linear layout
                linearLayout.addView(assign);
                linearLayoutPrice.addView(price);
                //extend linear layout for every textView inserted
                layoutHeight += 75;
            }
            linearLayout.getLayoutParams().height = layoutHeight;
            linearLayoutPrice.getLayoutParams().height = layoutHeight;
            linearLayout.getLayoutParams().width = 500;
        }


    }
}