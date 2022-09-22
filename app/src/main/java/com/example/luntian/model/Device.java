package com.example.luntian.model;

import androidx.arch.core.internal.SafeIterableMap;

import java.util.ArrayList;
import java.util.List;

public class Device {


    public Device(boolean fan_status, int humidity, int minTemp, int sensor1Min, int sensor2Min, int sensor3Min, int sensor4Min, int temperature, int minSoilMoist4, int minSoilMoist3, int minSoilMoist2, int minSoilMoist1, int currentSoilMoist4, int currentSoilMoist3, int currentSoilMoist2, int currentSoilMoist1, ArrayList usersInvolved) {
        this.fan_status = fan_status;
        this.humidity = humidity;
        this.minTemp = minTemp;
        this.sensor1Min = sensor1Min;
        this.sensor2Min = sensor2Min;
        this.sensor3Min = sensor3Min;
        this.sensor4Min = sensor4Min;
        this.temperature = temperature;
        this.minSoilMoist4 = minSoilMoist4;
        this.minSoilMoist3 = minSoilMoist3;
        this.minSoilMoist2 = minSoilMoist2;
        this.minSoilMoist1 = minSoilMoist1;
        this.currentSoilMoist4 = currentSoilMoist4;
        this.currentSoilMoist3 = currentSoilMoist3;
        this.currentSoilMoist2 = currentSoilMoist2;
        this.currentSoilMoist1 = currentSoilMoist1;
        this.usersInvolved = usersInvolved;
    }

    public boolean fan_status;
    public int humidity;
    public int minTemp;
    public int sensor1Min;
    public int sensor2Min;
    public int sensor3Min;
    public int sensor4Min;
    public int temperature;

    // Minimum Moist
    public int minSoilMoist4;
    public int minSoilMoist3;
    public int minSoilMoist2;
    public int minSoilMoist1;
    // Current Moist
    public int currentSoilMoist4;
    public int currentSoilMoist3;
    public int currentSoilMoist2;
    public int currentSoilMoist1;

    public ArrayList usersInvolved;






    public Device() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }




}
