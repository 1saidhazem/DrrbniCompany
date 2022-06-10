package com.example.drrbnicompany.Models;

import com.google.firebase.Timestamp;

public class Ads {

    private String adsId,userId, adsTitle,major,adsRequirements,adsDescription, img;
    private Timestamp timestamp;

    public Ads() {
    }

    public Ads(String adsId, String userId, String adsTitle, String major, String adsRequirements, String adsDescription, String img, Timestamp timestamp) {
        this.adsId = adsId;
        this.userId = userId;
        this.adsTitle = adsTitle;
        this.major = major;
        this.adsRequirements = adsRequirements;
        this.adsDescription = adsDescription;
        this.img = img;
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAdsId() {
        return adsId;
    }

    public void setAdsId(String adsId) {
        this.adsId = adsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdsTitle() {
        return adsTitle;
    }

    public void setAdsTitle(String adsTitle) {
        this.adsTitle = adsTitle;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAdsRequirements() {
        return adsRequirements;
    }

    public void setAdsRequirements(String adsRequirements) {
        this.adsRequirements = adsRequirements;
    }

    public String getAdsDescription() {
        return adsDescription;
    }

    public void setAdsDescription(String adsDescription) {
        this.adsDescription = adsDescription;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
