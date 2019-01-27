package com.example.billviewer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    float x1,x2,y1,y2;
    protected Button viewGradeButton = null;
    protected TextView textView = null;

    public static final String RECEIPT_KEY = "name";
    private CollectionReference receiptRef = FirebaseFirestore.getInstance().collection("receipt");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        viewCamera();
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
                if(x1>x2){
                    viewBill();
                }
                break;
        }
        return false;
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
//          fetchData();
            viewBill();

        }
    };

    private void viewBill(){
        Intent intent = new Intent(this, ViewBill.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void viewCamera() {


        final CameraSource cameraSource;
        BarcodeDetector barcodeDetector;
        SurfaceView surfaceView;

        surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1080).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                try{
                    cameraSource.start(holder);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                int i=0;
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                cameraSource.stop();
                if(qrCodes.size() != 0){

                    i++;
                    if(i ==1) {

                        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(500);

                        viewBill();
                    }

                }
            }
        });
    }
}