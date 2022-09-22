package com.example.luntian.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luntian.R;
import com.example.luntian.model.RemarkModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class RemarkAdapter extends FirebaseRecyclerAdapter<RemarkModel, RemarkAdapter.rViewHolder> {
    public RemarkAdapter(@NonNull FirebaseRecyclerOptions<RemarkModel> remarklist) {
        super(remarklist);
    }

    @Override
    protected void onBindViewHolder(@NonNull RemarkAdapter.rViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull RemarkModel remarkModel) {

        holder.remarkDate.setText(remarkModel.getRemarkDate());
        holder.remarkTxt.setText(remarkModel.getRemarkTxt());
        Picasso.get().load(remarkModel.getRemarkImg()).into(holder.remarkImg);

        String plantID = remarkModel.getRemarkKey();

        Button del = holder.itemView.findViewById(R.id.delRemark);
        Button cancel = holder.itemView.findViewById(R.id.cancelDel);

        View delLayout = holder.itemView.findViewById(R.id.delRemarkLayout);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delLayout.setVisibility(View.VISIBLE);
                Context context = v.getContext();
                String mKey = getRef(position).getKey();
               // Toast.makeText(context, mKey, Toast.LENGTH_SHORT).show();
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase mDB = FirebaseDatabase.getInstance();
                        DatabaseReference mRef = mDB.getReference(userID).child("TrackPlantRemarks");
                        mRef.child(plantID).child(mKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Remark deleted!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Remark failed to delete!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                return false;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delLayout.setVisibility(View.GONE);
            }
        });
    }


    @NonNull
    @Override
    public RemarkAdapter.rViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remark_layout, parent, false);
        return new rViewHolder (view);
    }

    class rViewHolder extends RecyclerView.ViewHolder{
        TextView remarkDate;
        TextView remarkTxt;
        ImageView remarkImg;
        //Button del, cancel;
        public rViewHolder(@NonNull View itemView){
            super(itemView);
            remarkDate = itemView.findViewById(R.id.trackingDate);
            remarkTxt = itemView.findViewById(R.id.trackingRemark);
            remarkImg = itemView.findViewById(R.id.trackRemarkImg);
           // del = itemView.findViewById(R.id.delRemark);
           // cancel = itemView.findViewById(R.id.cancelDel);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();



    }
}

























