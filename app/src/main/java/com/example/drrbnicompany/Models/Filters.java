package com.example.drrbnicompany.Models;

public class Filters {

    private String major = null;

    public Filters() {}

    public Filters( String major) {
        this.major = major;

    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean hasMajor() {
        return (major != null);
    }

}
