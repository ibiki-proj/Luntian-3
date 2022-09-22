package com.example.luntian.model;

public class Reminder {
    String currentDate;
    String t;
    String d;
    String tm;
    String dt;
    String key;

    public Reminder(String t, String d, String tm, String dt, String currentDate) {
        this.t = t;
        this.d = d;
        this.tm = tm;
        this.dt = dt;
        this.currentDate = currentDate;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String gett() {
        return t;
    }

    public String getd() {
        return d;
    }

    public String gettm() {
        return tm;
    }

    public String getdt() {
        return dt;
    }

    public String getcurrentDate() {
        return currentDate;
    }
    public Reminder() {
    }
}
