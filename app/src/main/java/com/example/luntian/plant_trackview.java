package com.example.luntian;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luntian.adapter.RemarkAdapter;
import com.example.luntian.model.GrowthModel;
import com.example.luntian.model.RemarkModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class plant_trackview extends AppCompatActivity {

    ImageView backPlantBtn, editBtn;
    TextView trackName, trackDate, refId, cancelGrowth, cancelRemarks;
    ImageView plantImg, remarkImg;
    String imgUri;
    Button addGrowthBtn, addHeightBtn, addRemarkLayout, addRemarks;
    EditText graphDate;
    EditText height;
    EditText day;
    View addLayout, remarkLayout, noRemarksLayout, fillRemarksLayout;

    FirebaseStorage storage;
    StorageReference storageReference;

    private static final int img_code = 1;
    private Uri imageUri = null;

    //for graphDate
    // int dayDiff;
    Calendar graphCalendar;
    SimpleDateFormat sdf;
    String dateGraph;

    EditText remarkDate, remarkTxt;

    GraphView graphView;
    LineGraphSeries series;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference, mRef2;

    //private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    // private String date;

    RecyclerView remarkListview;
    RemarkAdapter remarkAdapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_trackview);

        plantImg = findViewById(R.id.trackImg);
        trackName = findViewById(R.id.trackPlantName);
        trackDate = findViewById(R.id.trackDatePlanted);
        refId = findViewById(R.id.referenceID);

        Intent rInt = getIntent();
        String name = rInt.getStringExtra("PlantName");
        String pdate = rInt.getStringExtra("DatePlanted");
        String id = rInt.getStringExtra("id");

        imgUri = rInt.getStringExtra("Image");
        String pstatus = rInt.getStringExtra("PlantStatus");
        String childKey = rInt.getStringExtra("key");

        Picasso.get().load(imgUri).into(plantImg);
        trackName.setText(name);
        trackDate.setText(pdate);
        refId.setText(id);

        backPlantBtn = findViewById(R.id.plantbackBtn);
        backPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(plant_trackview.this, plant_growth_tracking.class);
                startActivity(intent);
                finish();
            }
        });

        editBtn = findViewById(R.id.editTrackBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plant_trackview.this, edit_planttrack.class);
                intent.putExtra("plantID", id);
                intent.putExtra("plantName", name);
                intent.putExtra("plantDate", pdate);
                intent.putExtra("plantStatus", pstatus);
                intent.putExtra("childKey", childKey);
                startActivity(intent);
            }
        });

        addGrowthBtn = findViewById(R.id.addGrowthBtn);
        addHeightBtn = findViewById(R.id.addHeightBtn);
        addLayout = findViewById(R.id.addGrowthLayout);

        remarkDate = findViewById(R.id.remarkDate);
        remarkTxt = findViewById(R.id.remarkTxt);

        addGrowthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addLayout.setVisibility(View.VISIBLE);
                addGrowthBtn.setVisibility(View.GONE);
            }
        });
        cancelGrowth = findViewById(R.id.cancelGrowthbtn);
        cancelGrowth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLayout.setVisibility(View.GONE);
                addGrowthBtn.setVisibility(View.VISIBLE);
            }
        });

        //establish graphView
        day = findViewById(R.id.graphDay);
        height = findViewById(R.id.graphHeight);

        //get current date then subtract to date planted to get exact day of growth
        graphDate = findViewById(R.id.graphDate);
        graphCalendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        dateGraph = sdf.format(graphCalendar.getTime());
        graphDate.setText(dateGraph);

        //gets the day differences between current date and the date the plant is planted

        try {
            String date1 = trackDate.getText().toString();
            Date plantDate = sdf.parse(date1);
            String date2 = graphDate.getText().toString();
            Date currentDate = sdf.parse(date2);
            long dateDiff = currentDate.getTime() - plantDate.getTime();
            int dayDiff = (int) (dateDiff / (1000 * 60 * 60 * 24));
            day.setText(String.valueOf(dayDiff));
            // Toast.makeText(plant_trackview.this, "The day difference is "+dayDiff, Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                graphCalendar.set(Calendar.YEAR, year);
                graphCalendar.set(Calendar.MONTH, month);
                graphCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd/yyyy");
                graphDate.setText(mFormat.format(graphCalendar.getTime()));
            }
        };
        graphDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(plant_trackview.this, dpd, graphCalendar.get(Calendar.YEAR), graphCalendar.get(Calendar.MONTH),
                        graphCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        graphDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String date1 = trackDate.getText().toString();
                    Date plantDate = sdf.parse(date1);
                    String date2 = graphDate.getText().toString();
                    Date currentDate = sdf.parse(date2);
                    long dateDiff = currentDate.getTime() - plantDate.getTime();
                    int dayDiff = (int) (dateDiff / (1000 * 60 * 60 * 24));
                    day.setText(String.valueOf(dayDiff));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

/*
        graphDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase mDB = FirebaseDatabase.getInstance();
                DatabaseReference mRef = mDB.getReference(userID).child("PlantGrowthRate").child(id);
                Query checkDay = mRef.orderByKey().limitToLast(1);
                checkDay.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String val = "day";
                            String lastVal = String.valueOf(snapshot.child(val).getValue());
                            Toast.makeText(plant_trackview.this, "Last day added is "+lastVal, Toast.LENGTH_LONG).show();
                            /*int dayValue = Integer.parseInt(day.getText().toString());
                            String val = "day";

                            int lastValue = Integer.parseInt(lastVal);
                              if(dayValue < lastValue){
                                graphDate.setError("Input date already covered from past input!");
                                graphDate.requestFocus();
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {




            }
        });
        */


        //for plant growth graph
        graphView = findViewById(R.id.heightGraphview);
        series = new LineGraphSeries();
        graphView.addSeries(series);
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Day");
        gridLabel.setVerticalAxisTitle("Height");


        graphView.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graphView.getViewport().setScrollable(true);  // activate horizontal scrolling
        graphView.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graphView.getViewport().setScrollableY(true); // activate vertical scrolling

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference(userID).child("PlantGrowthRate").child(id);

        setListeners();


        //add remarks view
        addRemarkLayout = findViewById(R.id.addRemarkLayout);
        remarkLayout = findViewById(R.id.addRemarksLayout);
        addRemarks = findViewById(R.id.addRemarkBtn);

        addRemarkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remarkLayout.setVisibility(View.VISIBLE);
                addRemarkLayout.setVisibility(View.GONE);
            }
        });
        cancelRemarks = findViewById(R.id.cancelRemarkBtn);
        cancelRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remarkLayout.setVisibility(View.GONE);
                addRemarkLayout.setVisibility(View.VISIBLE);
            }
        });

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(calendar.getTime());
        remarkDate.setText(date);
        DatePickerDialog.OnDateSetListener currentDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
                remarkDate.setText(myFormat.format(calendar.getTime()));
            }
        };
        remarkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(plant_trackview.this, currentDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        noRemarksLayout = findViewById(R.id.noRemarksLayout);
        fillRemarksLayout = findViewById(R.id.remarksLayout);

        remarkImg = findViewById(R.id.plantRemarkImg);
        remarkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, img_code);
            }
        });

        pd = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();
        String refId = mReference.push().getKey();

        addRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String date = remarkDate.getText().toString().trim();
                String remark = remarkTxt.getText().toString().trim();

                if (remarkImg.getDrawable() == null){
                    Toast.makeText(plant_trackview.this, "Please choose an image for your remark.", Toast.LENGTH_LONG).show();
                    return;
                } else if (remark.isEmpty()){
                    remarkTxt.setError("Please add remark description.");
                    remarkTxt.requestFocus();
                } else {
                    pd.setTitle("Adding remark...");
                    pd.show();

                    storageReference = storage.getReference(userID).child("RemarkImage").child(imageUri.getLastPathSegment());
                    storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    mDatabase = FirebaseDatabase.getInstance();
                                    mReference = mDatabase.getReference(userID).child("TrackPlantRemarks").child(id);
                                    //String refId = mReference.push().getKey();

                                    DatabaseReference newPost = mReference.push();
                                    newPost.child("RemarkKey").setValue(id);
                                    //newPost.child("PlantID").setValue(id);
                                    newPost.child("RemarkDate").setValue(date);
                                    newPost.child("RemarkTxt").setValue(remark);
                                    newPost.child("RemarkImg").setValue(task.getResult().toString());

                                    pd.dismiss();
                                    Toast.makeText(plant_trackview.this, "New plant remark added!", Toast.LENGTH_SHORT).show();


                                    remarkLayout.setVisibility(View.GONE);
                                    addRemarkLayout.setVisibility(View.VISIBLE);

                                    remarkTxt.getText().clear();
                                    remarkImg.setImageDrawable(null);

                                    noRemarksLayout.setVisibility(View.GONE);
                                    fillRemarksLayout.setVisibility(View.VISIBLE);

                                }
                            });
                        }
                    });

                }


            }
        });


        remarkListview = findViewById(R.id.remarksView);
        // String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        remarkListview.setLayoutManager(new LinearLayoutManager(plant_trackview.this));
        FirebaseRecyclerOptions<RemarkModel> remarklist =
                new FirebaseRecyclerOptions.Builder<RemarkModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(userID).child("TrackPlantRemarks").child(id), RemarkModel.class)
                        .build();
        remarkAdapter = new RemarkAdapter(remarklist);
        remarkAdapter.startListening();
        remarkListview.setAdapter(remarkAdapter);


        mRef2 = mDatabase.getReference(userID).child("TrackPlantRemarks").child(id);
        Query query2 = mRef2.orderByChild(id);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    fillRemarksLayout.setVisibility(View.VISIBLE);
                    noRemarksLayout.setVisibility(View.GONE);
                    remarkAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        remarkAdapter.notifyDataSetChanged();

    }


    private void setListeners() {
        addHeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rID = mReference.push().getKey();
                String heightY = height.getText().toString();


                if (heightY.isEmpty()){
                    height.setError("Please add plant height measurement.");
                    height.requestFocus();
                } else {

                    //DatabaseReference newPost = mReference.push();
                    //newPost.child("dateAdded").setValue(dateAddedVal);
                    //newPost.child("day").setValue(dayValueX);
                    //newPost.child("height").setValue(heightValueY);

                    float dayValueX = Float.parseFloat(day.getText().toString());
                    float heightValueY = Float.parseFloat(height.getText().toString());
                    String dateAddedVal = graphDate.getText().toString();

                    GrowthModel growthModel = new GrowthModel(dayValueX, heightValueY, dateAddedVal);
                    mReference.child(rID).setValue(growthModel);
                    addLayout.setVisibility(View.GONE);
                    addGrowthBtn.setVisibility(View.VISIBLE);
                    height.getText().clear();
                }
                }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        remarkAdapter.startListening();
        remarkAdapter.notifyDataSetChanged();

        //sets data points in the graphview with data from database
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataPoint[] dp = new DataPoint[(int) snapshot.getChildrenCount()];
                int index = 0;

                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    GrowthModel growthModel = mDataSnapshot.getValue(GrowthModel.class);
                    dp[index] = new DataPoint(growthModel.getDay(), growthModel.getHeight());
                    index++;
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        remarkAdapter.stopListening();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == img_code && resultCode == RESULT_OK) {
            imageUri = data.getData();
            remarkImg.setImageURI(imageUri);
        }
    }
}

