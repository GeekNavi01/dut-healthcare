package com.example.duth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class UpdateAppointmentActivity extends AppCompatActivity {

    EditText visitReason, symptoms, service, blood_type;
    String id, reason, symptom, service_, bloodType, set_date, set_time;
    TextView appDate, appTime;
    Button submit;
    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit appointment");

//        visitReason = findViewById(R.id.update_reason_edit);
        symptoms = findViewById(R.id.update_symptoms_edit);
        service = findViewById(R.id.update_service);
//        blood_type = findViewById(R.id.update_bloodType);
        appDate = findViewById(R.id.update_set_date);
        appTime = findViewById(R.id.update_set_time);
        submit = findViewById(R.id.update_submit_appointment);
        
        updateAppointment();

        Objects.requireNonNull(getSupportActionBar()).setTitle("Update");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userDBHelper = new UserDBHelper(UpdateAppointmentActivity.this);
                service_ = service.getText().toString().trim();
//                bloodType = blood_type.getText().toString().trim();
//                reason = visitReason.getText().toString().trim();
                symptom = symptoms.getText().toString().trim();
                set_date = appDate.getText().toString();
                set_time = appTime.getText().toString();
//                userDBHelper.updateAppointment(id, service_, bloodType, reason, symptom, set_date, set_time)
                userDBHelper.updateAppointment(id, service_, symptom, set_date, set_time);
                Toast.makeText(UpdateAppointmentActivity.this, "Appointment updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAppointment() {

        if(getIntent().hasExtra("appID") && getIntent().hasExtra("service") && getIntent().hasExtra("symptoms") && 
                    getIntent().hasExtra("set_date") && getIntent().hasExtra("set_time")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("appID");
            service_ = getIntent().getStringExtra("service");
//            reason = getIntent().getStringExtra("reason");
            symptom = getIntent().getStringExtra("symptoms");
            set_date = getIntent().getStringExtra("set_date");
            set_time = getIntent().getStringExtra("set_time");

            //Setting Intent Data
            service.setText(service_);
            symptoms.setText(symptom);
            appDate.setText(set_date);
            appTime.setText(set_time);
        }else{
            Toast.makeText(this, "Could not update appointment", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(UpdateAppointmentActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (itemID == R.id.nav_prof) {
            startActivity(new Intent(UpdateAppointmentActivity.this, ProfileActivity.class));
        } else if (itemID == R.id.nav_history) {
            Intent intent = new Intent(UpdateAppointmentActivity.this, HistoryActivity.class);
            startActivity(intent);
        } else if (itemID == R.id.nav_comment) {
            startActivity(new Intent(UpdateAppointmentActivity.this, CommentActivity.class));
        } else if (itemID == R.id.nav_logout) {
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UpdateAppointmentActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}