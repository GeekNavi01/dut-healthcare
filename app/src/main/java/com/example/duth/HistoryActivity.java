package com.example.duth;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {
    UserDBHelper userDBHelper;
    RecyclerView recyclerView;
    ArrayList<String> service, appID, symptoms, set_date, set_time;
    AdapterClass adapter;
    TextView setReminder;
    MaterialTimePicker materialTimePicker;
    AlarmManager alarmManager;
    Calendar calendar;
    RelativeLayout deleteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Objects.requireNonNull(getSupportActionBar()).setTitle("History");

        notificationChannel();

        recyclerView = findViewById(R.id.recycler_view);
        setReminder = findViewById(R.id.set_reminder);
        deleteLayout = findViewById(R.id.delete_app);
        userDBHelper = new UserDBHelper(HistoryActivity.this);

        service = new ArrayList<>();
//        reason = new ArrayList<>();
        appID = new ArrayList<>();
        symptoms = new ArrayList<>();
        set_date = new ArrayList<>();
        set_time = new ArrayList<>();

        retrieveData();

        adapter = new AdapterClass(HistoryActivity.this, this, service, appID, symptoms, set_date, set_time);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationTimePicker();
            }
        });

        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAppDialog();
            }
        });
    }

    void deleteAppDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setTitle("Delete Appointments?");
        builder.setMessage("Are you sure you want to delete all appointments?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userDBHelper = new UserDBHelper(HistoryActivity.this);
                userDBHelper.deleteAppointment();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private void notificationTimePicker() {
        materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Set reminder")
                .build();

        materialTimePicker.show(getSupportFragmentManager(), "Duthealthcare");
        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (materialTimePicker.getHour() > 12){
                    String formatted_time = String.format("%02d",(materialTimePicker.getHour()-12))+" : "+String.format("%02d",materialTimePicker
                            .getMinute())+" PM";
                    setReminder.setText(formatted_time);
                }else {
                    setReminder.setText(materialTimePicker.getHour()+" : "+materialTimePicker.getMinute()+ " AM");
                }

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(HistoryActivity.this, NotificationReminder.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(HistoryActivity.this, 0, intent, 0);

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                Toast.makeText(HistoryActivity.this, "Reminder set", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "dutHealthcareNotification";
            String desc = "Appointment reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("Duthealthcare", name, importance);
            notificationChannel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    void retrieveData(){
        userDBHelper = new UserDBHelper(HistoryActivity.this);
        Cursor cursor = userDBHelper.readAllAppointmentData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Could not fetch data at this moment", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                appID.add(cursor.getString(0));
                service.add(cursor.getString(2));
//                reason.add(cursor.getString(2));
                symptoms.add(cursor.getString(1));
                set_date.add(cursor.getString(3));
                set_time.add(cursor.getString(4));
            }
        }
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
            Intent intent = new Intent(HistoryActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (itemID == R.id.nav_prof) {
            startActivity(new Intent(HistoryActivity.this, ProfileActivity.class));
        } else if (itemID == R.id.nav_history) {
            Intent intent = new Intent(HistoryActivity.this, HistoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (itemID == R.id.nav_comment) {
            startActivity(new Intent(HistoryActivity.this, CommentActivity.class));
        } else if (itemID == R.id.nav_logout) {
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}