package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luntian.adapter.plantAdapter;
import com.example.luntian.model.PlantModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class plantcyclopedia extends AppCompatActivity {

    TextView homeTitle;
    Button addPlantInfoBtn;
    EditText searchView;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    FirebaseStorage storage;
    RecyclerView plantListView;
    plantAdapter plantadapter;
    List<PlantModel> plantModelList;

    Spinner sortSpn;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantcyclopedia);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.plantcyclopedia);

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

        plantListView = (RecyclerView) findViewById(R.id.plantListview);
        plantListView.setLayoutManager(new LinearLayoutManager(plantcyclopedia.this));

        FirebaseRecyclerOptions<PlantModel> options =
                new FirebaseRecyclerOptions.Builder<PlantModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Plants").orderByChild("PlantName"), PlantModel.class)
                        .build();
        plantadapter = new plantAdapter(options);
        plantListView.setAdapter(plantadapter);
        plantadapter.notifyDataSetChanged();

        searchView = findViewById(R.id.searchView);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });

        homeTitle = findViewById(R.id.appTitle);
        homeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(plantcyclopedia.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addPlantInfoBtn = findViewById(R.id.addPlantInfoBtn);
        addPlantInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(plantcyclopedia.this, add_plant_info.class);
                startActivity(intent);
            }
        });

        sortSpn = findViewById(R.id.sortPlant);
        String[] spnStr = new String[]{"ALL", "HOUSEPLANT", "PRODUCE", "FLOWER"};
        ArrayAdapter<String> sortAdapter =  new ArrayAdapter<>(this, R.layout.spinner_single, spnStr);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpn.setAdapter(sortAdapter);

        sortSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equals("ALL")){
                    FirebaseRecyclerOptions<PlantModel> options =
                            new FirebaseRecyclerOptions.Builder<PlantModel>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Plants").orderByChild("PlantName"), PlantModel.class)
                                    .build();
                    plantadapter = new plantAdapter(options);
                    plantadapter.startListening();
                    plantListView.setAdapter(plantadapter);
                }
                else if (item.equals("HOUSEPLANT")){
                    String sort = "Houseplant";
                    FirebaseRecyclerOptions<PlantModel> options =
                            new FirebaseRecyclerOptions.Builder<PlantModel>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Plants").orderByChild("PlantKind").equalTo(sort), PlantModel.class)
                                    .build();
                    plantadapter = new plantAdapter(options);
                    plantadapter.startListening();
                    plantListView.setAdapter(plantadapter);
                }
                else if (item.equals("PRODUCE")){
                    String sort = "Produce";
                    FirebaseRecyclerOptions<PlantModel> options =
                            new FirebaseRecyclerOptions.Builder<PlantModel>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Plants").orderByChild("PlantKind").equalTo(sort), PlantModel.class)
                                    .build();
                    plantadapter = new plantAdapter(options);
                    plantadapter.startListening();
                    plantListView.setAdapter(plantadapter);
                }
                else if (item.equals("FLOWER")){
                    String sort = "Flower";
                    FirebaseRecyclerOptions<PlantModel> options =
                            new FirebaseRecyclerOptions.Builder<PlantModel>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Plants").orderByChild("PlantKind").equalTo(sort), PlantModel.class)
                                    .build();
                    plantadapter = new plantAdapter(options);
                    plantadapter.startListening();
                    plantListView.setAdapter(plantadapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void filter(String textSearch) {
        //String upperFirst = textSearch.substring(0,1).toUpperCase() + textSearch.substring(1).toLowerCase(); textSearch + "\uf8ff"
        FirebaseRecyclerOptions<PlantModel> options =
                new FirebaseRecyclerOptions.Builder<PlantModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Plants").orderByChild("PlantName").startAt(textSearch)
                                .endAt(textSearch + "\uf8ff"), PlantModel.class)
                        .build();
        plantadapter = new plantAdapter(options);
        plantadapter.startListening();
        plantListView.setAdapter(plantadapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        plantadapter.startListening();
        plantadapter.notifyDataSetChanged();
    }
    @Override
    protected void onStop() {
        super.onStop();
        plantadapter.stopListening();

    }
}