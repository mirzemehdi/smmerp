package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoResponse {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("check")
    @Expose
    private String check;

    public InfoResponse() {
    }

    public InfoResponse(String name, String check) {
        this.name = name;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
