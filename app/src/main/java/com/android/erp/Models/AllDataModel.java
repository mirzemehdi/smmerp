package com.android.erp.Models;

public class AllDataModel {
    private String date;
    private boolean isDone;

    public AllDataModel() {
    }

    public AllDataModel(String date, boolean isDone) {
        this.date = date;
        this.isDone = isDone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
