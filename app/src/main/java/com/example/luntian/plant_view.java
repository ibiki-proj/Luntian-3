package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class plant_view extends AppCompatActivity {

    ImageView backBtn, view_img, infoBtn;
    TextView nameTxt;
    TextView scientificTxt;
    TextView originTxt;
    TextView commonTxt;
    TextView carelvlTxt;
    TextView typeTxt;
    TextView heightTxt;
    TextView floweringTxt;
    TextView toxicTxt;
    TextView temperatureTxt;
    TextView lightingTxt;
    TextView humidityTxt;
    TextView wateringTxt;
    TextView fertilizerTxt;
    TextView depthTxt;
    TextView propagationTxt;
    TextView pruningTxt;
    TextView repotTxt;

    Button addCurrentPlantBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_view);

        View infoLayout = findViewById(R.id.termLayout);
        infoBtn = findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(infoLayout.getVisibility() == View.GONE){
                    infoLayout.setVisibility(View.VISIBLE);
                } else{
                    infoLayout.setVisibility(View.GONE);
                }
            }
        });

        backBtn = findViewById(R.id.plantbackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String imgUri;
        view_img = findViewById(R.id.view_img);
        nameTxt = findViewById(R.id.nameTxt);
        scientificTxt = findViewById(R.id.view_scientific);
        originTxt = findViewById(R.id.originTxt);
        commonTxt = findViewById(R.id.commonTxt);
        carelvlTxt = findViewById(R.id.carelvlTxt);
        typeTxt = findViewById(R.id.typeTxt);
        heightTxt = findViewById(R.id.heightTxt);
        floweringTxt = findViewById(R.id.floweringTxt);
        toxicTxt = findViewById(R.id.toxicTxt);
        temperatureTxt = findViewById(R.id.temperatureTxt);
        lightingTxt = findViewById(R.id.lightingTxt);
        humidityTxt = findViewById(R.id.humidityTxt);
        wateringTxt = findViewById(R.id.wateringTxt);
        fertilizerTxt = findViewById(R.id.fertilizerTxt);
        depthTxt = findViewById(R.id. depthTxt);
        propagationTxt = findViewById(R.id.propagationTxt);
        pruningTxt = findViewById(R.id.pruningTxt);
        repotTxt = findViewById(R.id.repotTxt);

        Intent rIntent = getIntent();
        String name = rIntent.getStringExtra("PlantName");
        String scientific = rIntent.getStringExtra("Scientific");
        imgUri = rIntent.getStringExtra("Image");
        String origin = rIntent.getStringExtra("Origin");
        String common = rIntent.getStringExtra("CommonIn");
        String carelvl = rIntent.getStringExtra("CareLvl");
        String type = rIntent.getStringExtra("PlantType");
        String height = rIntent.getStringExtra("MaxHeight");
        String flowering = rIntent.getStringExtra("FloweringSeason");
        String toxic = rIntent.getStringExtra("ToxicFor");
        String temp = rIntent.getStringExtra("Temperature");
        String humid = rIntent.getStringExtra("Humidity");
        String lighting = rIntent.getStringExtra("Lighting");
        String watering = rIntent.getStringExtra("WateringFrequency");
        String fertilizer = rIntent.getStringExtra("Fertilizer");
        String depth = rIntent.getStringExtra("PlantDepth");
        String propagation = rIntent.getStringExtra("Propagation");
        String pruning = rIntent.getStringExtra("Pruning");
        String repot = rIntent.getStringExtra("Repotting");

        Picasso.get().load(imgUri).into(view_img);
        nameTxt.setText(name);
        scientificTxt.setText(scientific);
        originTxt.setText(origin);
        commonTxt.setText(common);
        carelvlTxt.setText(carelvl);
        typeTxt.setText(type);
        heightTxt.setText(height);
        floweringTxt.setText(flowering);
        toxicTxt.setText(toxic);
        temperatureTxt.setText(temp);
        humidityTxt.setText(humid);
        lightingTxt.setText(lighting);
        wateringTxt.setText(watering);
        fertilizerTxt.setText(fertilizer);
        depthTxt.setText(depth);
        propagationTxt.setText(propagation);
        pruningTxt.setText(pruning);
        repotTxt.setText(repot);


        addCurrentPlantBtn = findViewById(R.id.addCurrentPlantBtn);
        addCurrentPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(plant_view.this, add_plant_track.class);
                 in.putExtra("AddNewTrack" , name);
                 in.putExtra("AddNewImg", imgUri.toString());

                 startActivity(in);
                 finish();
            }
        });

    }
}