package com.example.luntian;

import java.time.LocalDate;

public class DateIncrementer {
    static public String tomorrow(String date) {
        return LocalDate.parse(date).plusDays(1).toString();
    }
}