package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Numbers {
    @SerializedName("nbInstagramChecked")
    @Expose
    private String nbInstagramChecked;
    @SerializedName("nbInstagramPosts")
    @Expose
    private String nbInstagramPosts;
    @SerializedName("nbFacebookChecked")
    @Expose
    private String nbFacebookChecked;
    @SerializedName("nbFacebookPosts")
    @Expose
    private String nbFacebookPosts;
    @SerializedName("nbTwitterChecked")
    @Expose
    private String nbTwitterChecked;
    @SerializedName("nbTwitterPosts")
    @Expose
    private String nbTwitterPosts;
    @SerializedName("nbLinkedinChecked")
    @Expose
    private String nbLinkedinChecked;
    @SerializedName("nbLinkedinPosts")
    @Expose
    private String nbLinkedinPosts;
    @SerializedName("nbPhotoChecked")
    @Expose
    private String nbPhotoChecked;
    @SerializedName("nbPhotoPosts")
    @Expose
    private String nbPhotoPosts;
    @SerializedName("nbVideoChecked")
    @Expose
    private String nbVideoChecked;
    @SerializedName("nbVideoPosts")
    @Expose
    private String nbVideoPosts;
    @SerializedName("nbSMSChecked")
    @Expose
    private String nbSMSChecked;
    @SerializedName("nbSMSPosts")
    @Expose
    private String nbSMSPosts;

    public Numbers() {
    }

    public Numbers(String nbInstagramChecked, String nbInstagramPosts, String nbFacebookChecked, String nbFacebookPosts, String nbTwitterChecked, String nbTwitterPosts, String nbLinkedinChecked, String nbLinkedinPosts, String nbPhotoChecked, String nbPhotoPosts, String nbVideoChecked, String nbVideoPosts, String nbSMSChecked, String nbSMSPosts) {
        this.nbInstagramChecked = nbInstagramChecked;
        this.nbInstagramPosts = nbInstagramPosts;
        this.nbFacebookChecked = nbFacebookChecked;
        this.nbFacebookPosts = nbFacebookPosts;
        this.nbTwitterChecked = nbTwitterChecked;
        this.nbTwitterPosts = nbTwitterPosts;
        this.nbLinkedinChecked = nbLinkedinChecked;
        this.nbLinkedinPosts = nbLinkedinPosts;
        this.nbPhotoChecked = nbPhotoChecked;
        this.nbPhotoPosts = nbPhotoPosts;
        this.nbVideoChecked = nbVideoChecked;
        this.nbVideoPosts = nbVideoPosts;
        this.nbSMSChecked = nbSMSChecked;
        this.nbSMSPosts = nbSMSPosts;
    }

    public String getNbInstagramChecked() {
        return nbInstagramChecked;
    }

    public void setNbInstagramChecked(String nbInstagramChecked) {
        this.nbInstagramChecked = nbInstagramChecked;
    }

    public String getNbInstagramPosts() {
        return nbInstagramPosts;
    }

    public void setNbInstagramPosts(String nbInstagramPosts) {
        this.nbInstagramPosts = nbInstagramPosts;
    }

    public String getNbFacebookChecked() {
        return nbFacebookChecked;
    }

    public void setNbFacebookChecked(String nbFacebookChecked) {
        this.nbFacebookChecked = nbFacebookChecked;
    }

    public String getNbFacebookPosts() {
        return nbFacebookPosts;
    }

    public void setNbFacebookPosts(String nbFacebookPosts) {
        this.nbFacebookPosts = nbFacebookPosts;
    }

    public String getNbTwitterChecked() {
        return nbTwitterChecked;
    }

    public void setNbTwitterChecked(String nbTwitterChecked) {
        this.nbTwitterChecked = nbTwitterChecked;
    }

    public String getNbTwitterPosts() {
        return nbTwitterPosts;
    }

    public void setNbTwitterPosts(String nbTwitterPosts) {
        this.nbTwitterPosts = nbTwitterPosts;
    }

    public String getNbLinkedinChecked() {
        return nbLinkedinChecked;
    }

    public void setNbLinkedinChecked(String nbLinkedinChecked) {
        this.nbLinkedinChecked = nbLinkedinChecked;
    }

    public String getNbLinkedinPosts() {
        return nbLinkedinPosts;
    }

    public void setNbLinkedinPosts(String nbLinkedinPosts) {
        this.nbLinkedinPosts = nbLinkedinPosts;
    }

    public String getNbPhotoChecked() {
        return nbPhotoChecked;
    }

    public void setNbPhotoChecked(String nbPhotoChecked) {
        this.nbPhotoChecked = nbPhotoChecked;
    }

    public String getNbPhotoPosts() {
        return nbPhotoPosts;
    }

    public void setNbPhotoPosts(String nbPhotoPosts) {
        this.nbPhotoPosts = nbPhotoPosts;
    }

    public String getNbVideoChecked() {
        return nbVideoChecked;
    }

    public void setNbVideoChecked(String nbVideoChecked) {
        this.nbVideoChecked = nbVideoChecked;
    }

    public String getNbVideoPosts() {
        return nbVideoPosts;
    }

    public void setNbVideoPosts(String nbVideoPosts) {
        this.nbVideoPosts = nbVideoPosts;
    }

    public String getNbSMSChecked() {
        return nbSMSChecked;
    }

    public void setNbSMSChecked(String nbSMSChecked) {
        this.nbSMSChecked = nbSMSChecked;
    }

    public String getNbSMSPosts() {
        return nbSMSPosts;
    }

    public void setNbSMSPosts(String nbSMSPosts) {
        this.nbSMSPosts = nbSMSPosts;
    }
}
