package com.example.billviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    protected Button viewGradeButton = null;
    protected TextView textView = null;

    public static final String RECEIPT_KEY = "name";
    private CollectionReference receiptRef = FirebaseFirestore.getInstance().collection("receipt");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    public void fetchData(){
        DocumentReference docRef = receiptRef.document("1");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String receipt = documentSnapshot.getString(RECEIPT_KEY);
                    textView.setText(receipt);
                }
            }
        });
    }

    //Initialize the layout components
    protected void setupUI(){
        textView = (TextView) findViewById(R.id.textView);
        viewGradeButton = (Button) findViewById(R.id.viewAllBillsButton);

        //Listen if the buttons is clicked
        viewGradeButton.setOnClickListener(onClickViewGradeButton);


    }

    private Button.OnClickListener onClickViewGradeButton = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
//            fetchData();
            viewBill();

        }
    };

    private void viewBill(){
        Intent intent = new Intent(this, ViewBill.class);
        startActivity(intent);
    }
}