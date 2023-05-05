package com.example.duth;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

// implements NavigationView.OnNavigationItemSelectedListener
public class AppointmentActivity extends AppCompatActivity {
    EditText visitReason, symptoms, service, blood_type;
    TextView username, set_date, set_time;
    Button submit;
    UserDBHelper userDBHelper;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener listener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    int Appyear, Appday, Appmonth, Apphour = 23, Appmins = 55;
    boolean is24HourFormat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Book appointment");

        set_date = findViewById(R.id.set_date);
        set_time = findViewById(R.id.set_time);
//        visitReason = findViewById(R.id.reason_edit);
        symptoms = findViewById(R.id.symptoms_edit);
        username = findViewById(R.id.user);
        service = findViewById(R.id.service);
//        blood_type = findViewById(R.id.bloodType);
        submit = findViewById(R.id.submit_appointment);

        userDBHelper = new UserDBHelper(AppointmentActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String reason = visitReason.getText().toString();
                String symptom = symptoms.getText().toString();
                String services = service.getText().toString();
//                String bloodType = blood_type.getText().toString();
                String appDate = set_date.getText().toString();
                String appTime = set_time.getText().toString();

//                if (TextUtils.isEmpty(reason)){
//                    visitReason.setError("Specify your visit reason");
//                    visitReason.requestFocus();
//                }
                if (TextUtils.isEmpty(symptom)) {
                    symptoms.setError("Specify the symptoms you are experiencing");
                    symptoms.requestFocus();
                } else if (TextUtils.isEmpty(services)) {
                    service.setError("Specify the service you require");
                    service.requestFocus();
                }
//                else if (TextUtils.isEmpty(bloodType)) {
//                    blood_type.setError("Specify your blood type");
//                    blood_type.requestFocus();
//                }
                else if (TextUtils.isEmpty(appDate)) {
                    set_date.setError("Please set the date for your appointment");
                } else if (TextUtils.isEmpty(appTime)) {
                    set_time.setError("Please set the time for your appointment");
                } else{
//                    userDBHelper.addNewAppointment(reason, symptom, services, bloodType, appDate, appTime);
                    userDBHelper.addNewAppointment(symptom, services, appDate, appTime);
                    Toast.makeText(AppointmentActivity.this, "Appointment saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                }
            }
        });
        
        set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                Appday = calendar.get(Calendar.DAY_OF_MONTH);
                Appmonth = calendar.get(Calendar.MONTH);
                Appyear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener, Appday, Appmonth, Appyear);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fullDate = dayOfMonth+" - "+(month + 1)+" - "+year;
                set_date.setText(fullDate);
            }
        };

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, timeSetListener, Apphour, Appmins, is24HourFormat);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.setTitle("Set appointment time");
                timePickerDialog.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String fullTime = hourOfDay+" : "+minute;
                set_time.setText(fullTime);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        inflate menu items
        getMenuInflater().inflate(R.menu.nav_menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //    on item selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if (itemID == R.id.nav_home){
            Intent intent = new Intent(AppointmentActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (itemID == R.id.nav_prof) {
            startActivity(new Intent(AppointmentActivity.this, ProfileActivity.class));
        } else if (itemID == R.id.nav_history) {
            Intent intent = new Intent(AppointmentActivity.this, HistoryActivity.class);
            startActivity(intent);
        } else if (itemID == R.id.nav_comment) {
            startActivity(new Intent(AppointmentActivity.this, CommentActivity.class));
        } else if (itemID == R.id.nav_logout) {
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AppointmentActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}