package com.example.luntian.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luntian.R;
import com.example.luntian.model.PlantModel;
import com.example.luntian.plant_view;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class plantAdapter extends FirebaseRecyclerAdapter<PlantModel, plantAdapter.myViewholder> {

    public plantAdapter(@NonNull FirebaseRecyclerOptions<PlantModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, int i, @NonNull PlantModel plantModel) {

        holder.plantName.setText(plantModel.getPlantName());
        holder.plantScientific.setText(plantModel.getScientific());
        holder.plantKind.setText(plantModel.getPlantKind());
        Picasso.get().load(plantModel.getImage()).into(holder.plantImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, plant_view.class);
                String pName = plantModel.getPlantName();
                String pScientific = plantModel.getScientific();
                String pImg = plantModel.getImage();
                String pOrigin = plantModel.getOrigin();
                String pCommonIn = plantModel.getCommonIn();
                String pCarelvl = plantModel.getCareLvl();
                String pType = plantModel.getPlantType();
                String pHeight = plantModel.getMaxHeight();
                String pFlowering = plantModel.getFloweringSeason();
                String pToxic = plantModel.getToxicFor();
                String pTemp = plantModel.getTemperature();
                String pHumid = plantModel.getHumidity();
                String pLighting = plantModel.getLighting();
                String pWatering = plantModel.getWateringFrequency();
                String pFertilizer = plantModel.getFertilizer();
                String pDepth = plantModel.getPlantDepth();
                String pPropagation = plantModel.getPropagation();
                String pPruning = plantModel.getPruning();
                String pRepot = plantModel.getRepotting();
                String pKind = plantModel.getPlantKind();

                intent.putExtra("PlantName", pName);
                intent.putExtra("Scientific", pScientific);
                intent.putExtra("Image", pImg);
                intent.putExtra("Origin", pOrigin);
                intent.putExtra("CommonIn", pCommonIn);
                intent.putExtra("CareLvl", pCarelvl);
                intent.putExtra("PlantType", pType);
                intent.putExtra("MaxHeight", pHeight);
                intent.putExtra("FloweringSeason", pFlowering);
                intent.putExtra("ToxicFor", pToxic);
                intent.putExtra("Temperature", pTemp);
                intent.putExtra("Humidity", pHumid);
                intent.putExtra("Lighting", pLighting);
                intent.putExtra("WateringFrequency", pWatering);
                intent.putExtra("Fertilizer", pFertilizer);
                intent.putExtra("PlantDepth", pDepth);
                intent.putExtra("Propagation", pPropagation);
                intent.putExtra("Pruning", pPruning);
                intent.putExtra("Repotting", pRepot);
                intent.putExtra("PlantKind", pKind);

                context.startActivity(intent);
                
            }
        });
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantcard_layout2, parent, false);
        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{
        ImageView plantImg;
        TextView plantName;
        TextView plantScientific;
        TextView plantKind;
        public myViewholder(@NonNull View itemView){
            super(itemView);
            plantImg = itemView.findViewById(R.id.cardPlant_img);
            plantName = itemView.findViewById(R.id.cardPlant_name);
            plantScientific = itemView.findViewById(R.id.cardPlant_scientific);
            plantKind = itemView.findViewById(R.id.cardPlant_status);
        }
    }

}
