package com.example.drrbnicompany.Models;

public class Major {
    private String majorId,image,name;

    public Major() {}

    public Major(String majorId, String image, String name) {
        this.majorId = majorId;
        this.image = image;
        this.name = name;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
