package com.example.drrbnicompany.Models;

public class Student {
    private String email, img, name, major, college, governorate, address, whatsApp, UserId;
    private int typeUser;

    public Student(String email, String img, String name, String specialization, String college, String governorate, String address, String whatsApp, String UserId, int typeUser) {
        this.email = email;
        this.img = img;
        this.name = name;
        this.major = specialization;
        this.college = college;
        this.governorate = governorate;
        this.address = address;
        this.whatsApp = whatsApp;
        this.UserId = this.UserId;
        this.typeUser = typeUser;
    }

    public Student() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public int getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }
}
