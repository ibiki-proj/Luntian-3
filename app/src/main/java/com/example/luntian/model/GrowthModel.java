package com.example.luntian.model;

public class GrowthModel {
    float day, height;

    String dateAdded;

    public GrowthModel() {
    }

    public GrowthModel(float day, float height, String dateAdded) {
        this.day = day;
        this.height = height;
        this.dateAdded = dateAdded;
    }

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
