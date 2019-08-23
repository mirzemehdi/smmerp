package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("user")
    @Expose
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(String result, User user) {
        this.result = result;
        this.user = user;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
