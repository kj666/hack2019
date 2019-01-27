package com.example.billviewer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Dashboard extends AppCompatActivity {

    private float x1,x2,y1,y2;
    protected Button viewGradeButton_dash = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setupUI();

        PieChartView pieChartView = findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();

        pieData.add(new SliceValue(15, Color.BLUE).setLabel("Travel"));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Groceries"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Utilities"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Shopping"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Spendings").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);
    }

    private void setupUI(){
        viewGradeButton_dash = (Button) findViewById(R.id.viewAllBillsButton_dash);
        viewGradeButton_dash.setOnClickListener(onClickViewGradeButton);
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
                if(x2<1.5*x1)
                    finish();
                break;
        }
        return false;
    }


    private Button.OnClickListener onClickViewGradeButton = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
//          fetchData();
            viewBill();

        }
    };
    private void viewBill(){
        Intent intent = new Intent(this, ViewBill.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}