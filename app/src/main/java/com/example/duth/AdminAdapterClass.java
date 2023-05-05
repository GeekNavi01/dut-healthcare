package com.example.duth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminAdapterClass extends RecyclerView.Adapter<ViewHolderClass> {
    private final Context context;
    Activity activity;
    private final ArrayList service, appointmentID, symptoms;

//    reason,

//    public AdminAdapterClass(Activity activity, Context context, ArrayList service, ArrayList reason, ArrayList appointmentID, ArrayList symptoms)
    public AdminAdapterClass(Activity activity, Context context, ArrayList service, ArrayList appointmentID, ArrayList symptoms) {
        this.activity = activity;
        this.context = context;
        this.service = service;
//        this.reason = reason;
        this.appointmentID = appointmentID;
        this.symptoms = symptoms;
    }

    @NonNull
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_recycler_item, parent, false);
        return new ViewHolderClass(view);
    }

    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.recService.setText(String.valueOf(service.get(position)));
//        holder.recReason.setText(String.valueOf(reason.get(position)));
        holder.recSymptom.setText(String.valueOf(symptoms.get(position)));

        holder.recAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ApproveRejectActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("service", String.valueOf(service.get(holder.getAdapterPosition())));
                intent.putExtra("symptoms", String.valueOf(symptoms.get(holder.getAdapterPosition())));
//                intent.putExtra("reason", String.valueOf(reason.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentID.size();
    }
}

class ViewHolderClass extends RecyclerView.ViewHolder{
    TextView recService, recID, recReason, recSymptom, recAction;

    public ViewHolderClass(@NonNull View itemView) {
        super(itemView);

        recID = itemView.findViewById(R.id.recID);
        recAction = itemView.findViewById(R.id.admin_action);
//        recReason = itemView.findViewById(R.id.recReas);
        recService = itemView.findViewById(R.id.recService);
        recSymptom = itemView.findViewById(R.id.recSymptom);
    }
}