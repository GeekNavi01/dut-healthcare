package com.example.duth;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class AdminAppointmentsActivity extends AppCompatActivity {
    UserDBHelper userDBHelper;
    RecyclerView recyclerView;
    ArrayList<String> service, reason, appID, symptoms;
    AdminAdapterClass adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointments);

        Objects.requireNonNull(getSupportActionBar()).setTitle("User appointments");

        recyclerView = findViewById(R.id.recycler_view);
        userDBHelper = new UserDBHelper(AdminAppointmentsActivity.this);

        service = new ArrayList<>();
//        reason = new ArrayList<>();
        appID = new ArrayList<>();
        symptoms = new ArrayList<>();

        retrieveData();

//        adapter = new AdminAdapterClass(AdminAppointments.this, this, service, reason, appID, symptoms)
        adapter = new AdminAdapterClass(AdminAppointmentsActivity.this, this, service, appID, symptoms);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AdminAppointmentsActivity.this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    void retrieveData(){
        Cursor cursor = userDBHelper.adminReadAllAppointmentData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Could not fetch data at this moment", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                appID.add(cursor.getString(0));
                service.add(cursor.getString(2));
//                reason.add(cursor.getString(2));
                symptoms.add(cursor.getString(1));
            }
        }
    }
}