package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("advertisementPrice")
    @Expose
    private String advertisementPrice;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("checking")
    @Expose
    private String checking;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("userId")
    @Expose
    private String userId;

    public CategoriesResponse() {
    }

    public CategoriesResponse(String id, String title, String text, String advertisementPrice, String date, String checking, String categoryId, String userId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.advertisementPrice = advertisementPrice;
        this.date = date;
        this.checking = checking;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdvertisementPrice() {
        return advertisementPrice;
    }

    public void setAdvertisementPrice(String advertisementPrice) {
        this.advertisementPrice = advertisementPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChecking() {
        return checking;
    }

    public void setChecking(String checking) {
        this.checking = checking;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
