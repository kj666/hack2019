package com.example.billviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected Button viewGradeButton = null;
    protected TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    //Initialize the layout components
    protected void setupUI(){
        textView = (TextView) findViewById(R.id.textView);
        viewGradeButton = (Button) findViewById(R.id.viewGradeButton);

        //Listen if the buttons is clicked
        viewGradeButton.setOnClickListener(onClickViewGradeButton);
    }

    private Button.OnClickListener onClickViewGradeButton = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            viewGrades();
        }
    };

    private void viewGrades(){
//        Intent intent = new Intent(this, ViewGrade.class);
//        startActivity(intent);
    }
}