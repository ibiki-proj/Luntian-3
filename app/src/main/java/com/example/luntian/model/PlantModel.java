package com.example.luntian.model;

public class PlantModel {
    String Image;
    String PlantName;
    String Scientific;
    String Origin;
    String CommonIn;
    String CareLvl;
    String PlantType;
    String MaxHeight;
    String FloweringSeason;
    String ToxicFor;
    String Temperature;
    String Lighting;
    String Humidity;
    String WateringFrequency;
    String Fertilizer;
    String Propagation;
    String Pruning;
    String Repotting;
    String PlantDepth;



    String PlantKind;
    PlantModel(){

    }
    public PlantModel(String plantImg, String plantName, String plantScientific, String plantOrigin, String plantCommon, String plantCarelvl, String plantType,
                      String plantHeight, String plantFlowering, String plantToxic, String plantTemperature, String plantLighting, String plantHumidity, String plantWatering,
                      String plantFertilizer, String plantPropagation, String plantPruning, String plantRepot, String plantDepth, String plantKind) {

        Image = plantImg;
        PlantName = plantName;
        Scientific = plantScientific;
        Origin = plantOrigin;
        CommonIn = plantCommon;
        CareLvl = plantCarelvl;
        PlantType = plantType;
        MaxHeight = plantHeight;
        FloweringSeason = plantFlowering;
        ToxicFor = plantToxic;
        Temperature = plantTemperature;
        Lighting = plantLighting;
        Humidity = plantHumidity;
        WateringFrequency = plantWatering;
        Fertilizer = plantFertilizer;
        Propagation = plantPropagation;
        Pruning = plantPruning;
        Repotting = plantRepot;
        PlantDepth = plantDepth;
        PlantKind = plantKind;
    }


    public String getImage() {
        return Image;
    }


    public String getPlantName() {
        return PlantName;
    }

    public void setPlantName(String plantName) {
        PlantName = plantName;
    }

    public String getScientific() {
        return Scientific;
    }

    public void setScientific(String plantScientific) {
        Scientific = plantScientific;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String plantOrigin) {
        Origin = plantOrigin;
    }

    public String getCommonIn() {
        return CommonIn;
    }

    public void setCommonIn(String plantCommon) {
        CommonIn = plantCommon;
    }

    public String getCareLvl() {
        return CareLvl;
    }

    public void setCareLvl(String plantCarelvl) {
        CareLvl = plantCarelvl;
    }

    public String getPlantType() {
        return PlantType;
    }

    public void setPlantType(String plantType) {
        PlantType = plantType;
    }

    public String getMaxHeight() {
        return MaxHeight;
    }

    public void setMaxHeight(String plantHeight) {
        MaxHeight = plantHeight;
    }

    public String getFloweringSeason() {
        return FloweringSeason;
    }

    public void setFloweringSeason(String plantFlowering) {
        FloweringSeason = plantFlowering;
    }

    public String getToxicFor() {
        return ToxicFor;
    }

    public void setToxicFor(String plantToxic) {
        ToxicFor = plantToxic;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String plantTemperature) {
        Temperature = plantTemperature;
    }

    public String getLighting() {
        return Lighting;
    }

    public void setLighting(String plantLighting) {
        Lighting = plantLighting;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String plantHumidity) {
        Humidity = plantHumidity;
    }

    public String getWateringFrequency() {
        return WateringFrequency;
    }

    public void setWateringFrequency(String plantWatering) {
        WateringFrequency = plantWatering;
    }

    public String getFertilizer() {
        return Fertilizer;
    }

    public void setFertilizer(String plantFertilizer) {
        Fertilizer = plantFertilizer;
    }

    public String getPropagation() {
        return Propagation;
    }

    public void setPropagation(String plantPropagation) {
        Propagation = plantPropagation;
    }

    public String getPruning() {
        return Pruning;
    }

    public void setPruning(String plantPruning) {
        Pruning = plantPruning;
    }

    public String getRepotting() {
        return Repotting;
    }

    public void setRepotting(String plantRepot) {
        Repotting = plantRepot;
    }

    public String getPlantDepth() {
        return PlantDepth;
    }

    public void setPlantDepth(String plantDepth) {
        PlantDepth = plantDepth;
    }

    public String getPlantKind() {
        return PlantKind;
    }

    public void setPlantKind(String plantKind) {
        PlantKind = plantKind;
    }
}
