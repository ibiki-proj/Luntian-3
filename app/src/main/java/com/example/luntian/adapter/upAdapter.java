package com.example.luntian.adapter;

import android.annotation.SuppressLint;
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

public class upAdapter extends RecyclerView.Adapter<upAdapter.MyViewHolder> {

    Context context;

    ArrayList<Reminder> list;


    public upAdapter(Context context, ArrayList<Reminder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public upAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.upitem,parent,false);
        return  new upAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Reminder Reminder = list.get(position);
        holder.t.setText(Reminder.gett());
        holder.d.setText(Reminder.getd());
        holder.tm.setText(Reminder.gettm());
        holder.dt.setText(Reminder.getdt());
        holder.currentDate.setText(Reminder.getcurrentDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = list.get(position).getKey();
                Context context = v.getContext();

            }
        });



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

