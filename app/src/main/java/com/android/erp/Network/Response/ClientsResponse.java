package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientsResponse {
    @SerializedName("users")
    @Expose
    private List<Users> users = null;
    @SerializedName("pakets")
    @Expose
    private List<Paket> pakets = null;

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Paket> getPakets() {
        return pakets;
    }

    public void setPakets(List<Paket> pakets) {
        this.pakets = pakets;
    }
}
