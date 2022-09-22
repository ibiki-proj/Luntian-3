package com.example.luntian;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_plant_info extends AppCompatActivity {


    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView plantImg;
    EditText plant_nameTxt, plant_scientificTxt, plant_originTxt, plant_commonTxt, plant_carelvlTxt, plant_typeTxt, plant_heightTxt, plant_floweringTxt, plant_toxicTxt, plant_temperatureTxt,
            plant_lightingTxt, plant_humidityTxt, plant_wateringTxt, plant_fertilizerTxt, plant_propagationTxt, plant_pruningTxt, plant_repotTxt, plant_depthTxt;
    EditText plant_kindTxt;
    Button addPlantDBBtn;
    private static final int img_code=1;
    public Uri imageUri=null;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_info);

        /*storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();*/
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Plants");
        storage = FirebaseStorage.getInstance();
        plantImg = findViewById(R.id.plant_img);

        /* variable declaration */
        plant_nameTxt = findViewById(R.id.plant_name);
        plant_scientificTxt = findViewById(R.id.plant_scientific);
        plant_originTxt = findViewById(R.id.plant_origin);
        plant_commonTxt = findViewById(R.id.plant_common);
        plant_carelvlTxt = findViewById(R.id.plant_carelvl);
        plant_typeTxt = findViewById(R.id.plant_type);
        plant_heightTxt = findViewById(R.id.plant_height);
        plant_floweringTxt = findViewById(R.id.plant_flowering);
        plant_toxicTxt = findViewById(R.id.plant_toxic);
        plant_temperatureTxt = findViewById(R.id.plant_temperature);
        plant_lightingTxt = findViewById(R.id.plant_lighting);
        plant_humidityTxt = findViewById(R.id.plant_humidity);
        plant_wateringTxt = findViewById(R.id.plant_watering);
        plant_fertilizerTxt = findViewById(R.id.plant_fertilizer);
        plant_propagationTxt = findViewById(R.id.plant_propagation);
        plant_pruningTxt = findViewById(R.id.plant_pruning);
        plant_repotTxt = findViewById(R.id.plant_repot);
        plant_depthTxt = findViewById(R.id.plant_depth);

        plant_kindTxt = findViewById(R.id.plant_kind);

        pd = new ProgressDialog(this);

        addPlantDBBtn = findViewById(R.id.addPlantDBBtn);

        plantImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, img_code);
            }
        });



        addPlantDBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = plant_nameTxt.getText().toString().trim();
                String scientificTxt = plant_scientificTxt.getText().toString().trim();
                String originTxt = plant_originTxt.getText().toString().trim();
                String commonTxt = plant_commonTxt.getText().toString().trim();
                String carelvlTxt = plant_carelvlTxt.getText().toString().trim();
                String typeTxt = plant_typeTxt.getText().toString().trim();
                String heightTxt = plant_heightTxt.getText().toString().trim();
                String floweringTxt = plant_floweringTxt.getText().toString().trim();
                String toxicTxt = plant_toxicTxt.getText().toString().trim();
                String temperatureTxt = plant_temperatureTxt.getText().toString().trim();
                String lightingTxt = plant_lightingTxt.getText().toString().trim();
                String humidityTxt = plant_humidityTxt.getText().toString().trim();
                String wateringTxt = plant_wateringTxt.getText().toString().trim();
                String fertilizerTxt = plant_fertilizerTxt.getText().toString().trim();
                String propagationTxt = plant_propagationTxt.getText().toString().trim();
                String pruningTxt = plant_pruningTxt.getText().toString().trim();
                String repotTxt = plant_repotTxt.getText().toString().trim();
                String depthTxt = plant_depthTxt.getText().toString().trim();

                String kindTxt = plant_kindTxt.getText().toString().trim();

                pd.setTitle("Uploading Data...");
                pd.show();

                storageReference = storage.getReference().child("PlantImage").child(imageUri.getLastPathSegment());
                storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String t = task.getResult().toString();

                                DatabaseReference newPost = mReference.push();

                                newPost.child("Image").setValue(task.getResult().toString());
                                newPost.child("PlantName").setValue(nameTxt);
                                newPost.child("Scientific").setValue(scientificTxt);
                                newPost.child("Origin").setValue(originTxt);
                                newPost.child("CommonIn").setValue(commonTxt);
                                newPost.child("CareLvl").setValue(carelvlTxt);
                                newPost.child("PlantType").setValue(typeTxt);
                                newPost.child("MaxHeight").setValue(heightTxt);
                                newPost.child("FloweringSeason").setValue(floweringTxt);
                                newPost.child("ToxicFor").setValue(toxicTxt);
                                newPost.child("Temperature").setValue(temperatureTxt);
                                newPost.child("Lighting").setValue(lightingTxt);
                                newPost.child("Humidity").setValue(humidityTxt);
                                newPost.child("WateringFrequency").setValue(wateringTxt);
                                newPost.child("Fertilizer").setValue(fertilizerTxt);
                                newPost.child("Propagation").setValue(propagationTxt);
                                newPost.child("Pruning").setValue(pruningTxt);
                                newPost.child("Repotting").setValue(repotTxt);
                                newPost.child("PlantDepth").setValue(depthTxt);

                                newPost.child("PlantKind").setValue(kindTxt);
                                pd.dismiss();

                                Intent intent = new Intent(add_plant_info.this, plantcyclopedia.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
            }
        });







    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == img_code && resultCode == RESULT_OK){
            imageUri = data.getData();
            plantImg.setImageURI(imageUri);
        }
    }


}