package com.android.erp.Network.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Paket implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    protected Paket(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Paket> CREATOR = new Creator<Paket>() {
        @Override
        public Paket createFromParcel(Parcel in) {
            return new Paket(in);
        }

        @Override
        public Paket[] newArray(int size) {
            return new Paket[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
