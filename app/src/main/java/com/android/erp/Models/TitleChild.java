package com.android.erp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TitleChild   implements Parcelable {
    private String name;
    private String imgurl;
    private String categoryId;
    private int done,undone;

    public TitleChild() {
    }

    public TitleChild(String name, int done, int undone) {
        this.name = name;
        this.done = done;
        this.undone = undone;
    }

    public TitleChild(String name, int done, int undone,String imgurl,String categoryId) {
        this.name = name;
        this.imgurl = imgurl;
        this.done = done;
        this.undone = undone;
        this.categoryId=categoryId;
    }

    protected TitleChild(Parcel in) {
        name = in.readString();
        imgurl=in.readString();
        categoryId=in.readString();
        done = in.readInt();
        undone = in.readInt();
    }

    public static final Creator<TitleChild> CREATOR = new Creator<TitleChild>() {
        @Override
        public TitleChild createFromParcel(Parcel in) {
            return new TitleChild(in);
        }

        @Override
        public TitleChild[] newArray(int size) {
            return new TitleChild[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getUndone() {
        return undone;
    }

    public void setUndone(int undone) {
        this.undone = undone;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imgurl);
        dest.writeString(categoryId);
        dest.writeInt(done);
        dest.writeInt(undone);

    }
}
