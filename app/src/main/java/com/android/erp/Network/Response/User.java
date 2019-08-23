package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("isAdmin")
    @Expose
    private String isAdmin;
    @SerializedName("paketName")
    @Expose
    private String paketName;

    public User() {
    }

    public User(String userId, String displayname, String password, String username, String isAdmin, String paketName) {
        this.userId = userId;
        this.displayname = displayname;
        this.password = password;
        this.username = username;
        this.isAdmin = isAdmin;
        this.paketName = paketName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPaketName() {
        return paketName;
    }

    public void setPaketName(String paketName) {
        this.paketName = paketName;
    }
}
