package com.example.duth;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class ApproveRejectActivity extends AppCompatActivity {

//    public static final String ACCOUNT_SID = System.getenv("AC04f121a2e87f72ffede5193fe99d83f3");
//    public static final String AUTH_TOKEN = System.getenv("8fa7d1248acb98b15ed33f09153929ae");

    TextView service, symptoms, reason, appDate, appTime;
    UserDBHelper userDBHelper;
    Button approve, reject, approve1, reject1;
    EditText adminTo, adminMessage, adminTo1, adminMessage1;
    RelativeLayout firstRelative, secondRelative, thirdRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Set status");

        service = findViewById(R.id.admin_service);
        approve1 = findViewById(R.id.admin_send);
        reject1 = findViewById(R.id.admin_send1);
        symptoms = findViewById(R.id.admin_symptoms);
//        reason = findViewById(R.id.admin_reason);
        appDate = findViewById(R.id.admin_date);
        appTime = findViewById(R.id.admin_time);
        approve = findViewById(R.id.admin_approve);
        reject = findViewById(R.id.admin_reject);
        adminTo = findViewById(R.id.admin_to);
        adminMessage = findViewById(R.id.admin_message);
        reject = findViewById(R.id.admin_reject);
        adminTo1 = findViewById(R.id.admin_to1);
        adminMessage1 = findViewById(R.id.admin_message1);
        firstRelative = findViewById(R.id.firstRelative);
        secondRelative = findViewById(R.id.secondRelative);
        thirdRelative = findViewById(R.id.thirdRelative);

        retrieveData();

        Cursor cursor = userDBHelper.readUserData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "Could not fetch data at this moment", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){

                adminTo.setText(""+cursor.getString(3));
                adminTo1.setText(""+cursor.getString(3));

            }
        }

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstRelative.setVisibility(View.GONE);
                thirdRelative.setVisibility(View.GONE);
                secondRelative.setVisibility(View.VISIBLE);

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstRelative.setVisibility(View.GONE);
                secondRelative.setVisibility(View.GONE);
                thirdRelative.setVisibility(View.VISIBLE);

            }
        });

        approve1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//                Message message = Message.creator(
//                                new PhoneNumber("+12225559999"),
//                                new PhoneNumber(TWILIO_NUMBER),
//                                "Sample Twilio SMS using Java")
//                        .create();


                if (ContextCompat.checkSelfPermission(ApproveRejectActivity.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){

                    sendApprovedSMS();

                }else{
                    ActivityCompat.requestPermissions(ApproveRejectActivity.this, new String[]{Manifest.permission.SEND_SMS},
                            1);
                }
            }
        });

        reject1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ApproveRejectActivity.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){

                    sendRejectedSMS();

                }else{
                    ActivityCompat.requestPermissions(ApproveRejectActivity.this, new String[]{Manifest.permission.SEND_SMS},
                            1);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager
                .PERMISSION_GRANTED){
            sendApprovedSMS();
        }else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendRejectedSMS() {
        String userPhone = adminTo1.getText().toString();
        String message = adminMessage1.getText().toString();
        if (!userPhone.isEmpty() && !message.isEmpty()){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(userPhone, null, message, null, null);
            Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Notification was not sent", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendApprovedSMS() {
        if (!adminTo.toString().isEmpty() && !adminMessage.toString().isEmpty()){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(adminTo.getText().toString(), null, adminMessage.getText().toString(), null, null);
            Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Notification was not sent", Toast.LENGTH_SHORT).show();
        }
    }

    void retrieveData(){
        userDBHelper = new UserDBHelper(ApproveRejectActivity.this);
        Cursor cursor = userDBHelper.adminReadAllAppointmentData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Could not fetch data at this moment", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){

                service.setText("Service: "+cursor.getString(2));
                symptoms.setText("Symptoms: "+cursor.getString(1));
//                reason.setText("Reason: "+cursor.getString(4));
                appDate.setText("Date: "+cursor.getString(3));
                appTime.setText("Time: "+cursor.getString(4));
            }
        }
    }
}