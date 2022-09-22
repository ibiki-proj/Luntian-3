package com.example.luntian.model;

public class RemarkModel {
    String RemarkDate;
    String RemarkTxt;
    String RemarkImg;



    String RemarkKey;
    public RemarkModel() {
    }
    public RemarkModel(String remarkDate, String remarkTxt, String remarkImg, String remarkKey) {
        RemarkDate = remarkDate;
        RemarkTxt = remarkTxt;
        RemarkImg = remarkImg;
        RemarkKey = remarkKey;
    }

    public String getRemarkKey() {
        return RemarkKey;
    }

    public void setRemarkKey(String remarkKey) {
        RemarkKey = remarkKey;
    }

    public String getRemarkDate() {
        return RemarkDate;
    }

    public void setRemarkDate(String remarkDate) {
        RemarkDate = remarkDate;
    }

    public String getRemarkTxt() {
        return RemarkTxt;
    }

    public void setRemarkTxt(String remarkTxt) {
        RemarkTxt = remarkTxt;
    }

    public String getRemarkImg() {
        return RemarkImg;
    }

    public void setRemarkImg(String remarkImg) {
        RemarkImg = remarkImg;
    }

}






