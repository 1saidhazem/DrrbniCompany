package com.example.drrbnicompany.Models;

public class Company {
    private String email, img, name, category , governorate, address, whatsApp, UserId , token;
    private boolean activated , verified;
    private int typeUser;

    public Company() {
    }

    public Company(String email, String img, String name, String category,
                   String governorate, String address, String whatsApp,
                   String userId, String token, boolean activated, boolean verified, int typeUser) {
        this.email = email;
        this.img = img;
        this.name = name;
        this.category = category;
        this.governorate = governorate;
        this.address = address;
        this.whatsApp = whatsApp;
        UserId = userId;
        this.token = token;
        this.activated = activated;
        this.verified = verified;
        this.typeUser = typeUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        UserId = userId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }
}
