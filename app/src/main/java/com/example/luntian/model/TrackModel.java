package com.example.luntian.model;

public class TrackModel {


    String ID;
    String Image;
    String PlantName;
    String DatePlanted;
    String WaterFrequency;
    String PlantStatus;



    String WaterTime;
    TrackModel(){

    }
    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPlantName() {
        return PlantName;
    }

    public void setPlantName(String plantName) {
        PlantName = plantName;
    }

    public String getDatePlanted() {
        return DatePlanted;
    }

    public void setDatePlanted(String datePlanted) {
        DatePlanted = datePlanted;
    }
    public String getWaterFrequency() {
        return WaterFrequency;
    }

    public void setWaterFrequency(String waterFrequency) {
        WaterFrequency = waterFrequency;
    }

    public String getWaterTime() {
        return WaterTime;
    }

    public void setWaterTime(String waterTime) {
        WaterTime = waterTime;
    }


    public String getPlantStatus() {
        return PlantStatus;
    }

    public void setPlantStatus(String plantStatus) {
        PlantStatus = plantStatus;
    }

    public TrackModel(String plantID, String plantImg, String plantName, String plantDate, String plantWF,  String waterTime, String pStatus){
        ID = plantID;
        Image = plantImg;
        PlantName = plantName;
        DatePlanted = plantDate;
        WaterFrequency = plantWF;
        PlantStatus = pStatus;
        WaterTime = waterTime;


    }
}
