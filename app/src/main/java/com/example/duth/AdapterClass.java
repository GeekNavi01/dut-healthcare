package com.example.duth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<ViewHolder_> {
    private final Context context;
    Activity activity;
    private final ArrayList service, appointmentID, symptoms, date, time;

    public AdapterClass(Activity activity, Context context, ArrayList service, ArrayList appointmentID, ArrayList symptoms,
                        ArrayList date, ArrayList time) {
        this.activity = activity;
        this.context = context;
        this.service = service;
//        this.reason = reason;
        this.appointmentID = appointmentID;
        this.symptoms = symptoms;
        this.date = date;
        this.time = time;
    }

    @NonNull
    public ViewHolder_ onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder_(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder_ holder, int position) {
        holder.recService.setText(String.valueOf(service.get(position)));
//        holder.recReason.setText(String.valueOf(reason.get(position)));
        holder.recSymptom.setText(String.valueOf(symptoms.get(position)));
        holder.recDate.setText(String.valueOf(date.get(position)));
        holder.recTime.setText(String.valueOf(time.get(position)));

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAppointmentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("service", String.valueOf(service.get(holder.getAdapterPosition())));
//                intent.putExtra("reason", String.valueOf(reason.get(holder.getAdapterPosition())));
                intent.putExtra("symptoms", String.valueOf(symptoms.get(holder.getAdapterPosition())));
                intent.putExtra("date", String.valueOf(date.get(holder.getAdapterPosition())));
                intent.putExtra("time", String.valueOf(time.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentID.size();
    }
}

class ViewHolder_ extends RecyclerView.ViewHolder{
    TextView recService, recReason, recSymptom, recDate, recTime;
    CardView recCard;

    public ViewHolder_(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);
//        recReason = itemView.findViewById(R.id.recReas);
        recService = itemView.findViewById(R.id.recService);
        recSymptom = itemView.findViewById(R.id.recSymptom);
        recDate = itemView.findViewById(R.id.recDate);
        recTime = itemView.findViewById(R.id.recTime);
    }
}