package com.example.luntian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luntian.R;
import com.example.luntian.model.Reminder;

import java.util.ArrayList;

public class dialogAdapter extends RecyclerView.Adapter<dialogAdapter.MyViewHolder> {

    Context context;

    ArrayList<Reminder> list;


    public dialogAdapter(Context context, ArrayList<Reminder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public dialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.uptiem2,parent,false);
        MyViewHolder holder=new dialogAdapter.MyViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull dialogAdapter.MyViewHolder holder, int position) {

        Reminder Reminder = list.get(position);
        holder.t.setText(Reminder.gett());
        holder.d.setText(Reminder.getd());
        holder.tm.setText(Reminder.gettm());
        holder.dt.setText(Reminder.getdt());
        holder.currentDate.setText(Reminder.getcurrentDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView t, d, tm, dt, currentDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            t = itemView.findViewById(R.id.tvtitle);
            d = itemView.findViewById(R.id.tvdesc);
            tm = itemView.findViewById(R.id.tvtime);
            dt = itemView.findViewById(R.id.tvdate);
            currentDate = itemView.findViewById(R.id.tvcdate);




        }
    }

}