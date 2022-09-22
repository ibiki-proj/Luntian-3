package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luntian.adapter.PlantTrackAdapter;
import com.example.luntian.model.TrackModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class plant_growth_tracking extends AppCompatActivity {

    Button addPlantTrackBtn, viewTrackView;
    RecyclerView trackListView;
    PlantTrackAdapter trackAdapter;
    TextView homeTitle;
    Spinner spnSort;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_growth_tracking);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.planttracking);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);

                        return true;

                    case R.id.planttracking:
                        Intent intent2 = new Intent(getApplicationContext(), plant_growth_tracking.class);

                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.plantcyclopedia:
                        Intent intent3 = new Intent(getApplicationContext(), plantcyclopedia.class);

                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.planner:
                        Intent intent4 = new Intent(getApplicationContext(), ReminderMainActivity.class);

                        startActivity(intent4);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        Intent intent5 = new Intent(getApplicationContext(), Settings.class);

                        startActivity(intent5);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //Intent rIntent = getIntent();
       // String userID = rIntent.getStringExtra("UserID");
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        TextView uID = findViewById(R.id.userIDTxt);
        uID.setText(userID);

        homeTitle = findViewById(R.id.appTitle);
        homeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(plant_growth_tracking.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addPlantTrackBtn = findViewById(R.id.addPlantTrackBtn);
        addPlantTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(plant_growth_tracking.this, add_plant_track.class);
                startActivity(intent);
                finish();
            }
        });

        //shows list view of plants to track
        trackListView = findViewById(R.id.trackPlantListview);
        trackListView.setLayoutManager(new LinearLayoutManager(plant_growth_tracking.this));
        FirebaseRecyclerOptions<TrackModel> tracklist =
                new FirebaseRecyclerOptions.Builder<TrackModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(userID).child("TrackPlant"), TrackModel.class)
                        .build();
        trackAdapter = new PlantTrackAdapter(tracklist);
        trackListView.setAdapter(trackAdapter);
        trackAdapter.notifyDataSetChanged();

        //sorting plants if planted harvested or wilted
        spnSort = findViewById(R.id.spnSort);
        String[] sortStr = new String[]{"ALL", "PLANTED", "HARVESTED" };
        ArrayAdapter<String> sortAdapter =  new ArrayAdapter<>(this, R.layout.spinner_single, sortStr);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSort.setAdapter(sortAdapter);

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("ALL")){
                    FirebaseRecyclerOptions<TrackModel> tracklist =
                            new FirebaseRecyclerOptions.Builder<TrackModel>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference(userID).child("TrackPlant"), TrackModel.class)
                                    .build();
                    trackAdapter = new PlantTrackAdapter(tracklist);
                    trackListView.setAdapter(trackAdapter);
                    trackAdapter.startListening();
                    trackAdapter.notifyDataSetChanged();
                }
                else if (item.equals("PLANTED")){
                    String sort = "Planted";
                    FirebaseRecyclerOptions<TrackModel> tracklist =
                            new FirebaseRecyclerOptions.Builder<TrackModel>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference(userID).child("TrackPlant").orderByChild("PlantStatus").equalTo(sort), TrackModel.class)
                                    .build();
                    trackAdapter = new PlantTrackAdapter(tracklist);
                    trackListView.setAdapter(trackAdapter);
                    trackAdapter.startListening();
                    trackAdapter.notifyDataSetChanged();
                }
                else if (item.equals("HARVESTED")){
                    String sort = "Harvested";
                    FirebaseRecyclerOptions<TrackModel> tracklist =
                            new FirebaseRecyclerOptions.Builder<TrackModel>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference(userID).child("TrackPlant").orderByChild("PlantStatus").equalTo(sort), TrackModel.class)
                                    .build();
                    trackAdapter = new PlantTrackAdapter(tracklist);
                    trackListView.setAdapter(trackAdapter);
                    trackAdapter.startListening();
                    trackAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //for dev purposes currently not used
        viewTrackView = findViewById(R.id.viewTrackView);
        viewTrackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(plant_growth_tracking.this, plant_trackview.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        trackAdapter.startListening();
        trackAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onStop() {
        super.onStop();
        trackAdapter.stopListening();

    }
}