package com.example.drrbnicompany.Models;

import java.util.ArrayList;

public class Student {
    private String UserId , name , email , college ,
            major , address , governorate , whatsApp , img ;
    private int typeUser , recommenderNum ;
    private ArrayList<String> recommenders;

    public Student(String userId, String name, String email, String college, String major, String address, String governorate, String whatsApp, String img, int typeUser, int recommenderNum, ArrayList<String> recommenders) {
        UserId = userId;
        this.name = name;
        this.email = email;
        this.college = college;
        this.major = major;
        this.address = address;
        this.governorate = governorate;
        this.whatsApp = whatsApp;
        this.img = img;
        this.typeUser = typeUser;
        this.recommenderNum = recommenderNum;
        this.recommenders = recommenders;
    }

    public Student() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }

    public int getRecommenderNum() {
        return recommenderNum;
    }

    public void setRecommenderNum(int recommenderNum) {
        this.recommenderNum = recommenderNum;
    }

    public ArrayList<String> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(ArrayList<String> recommenders) {
        this.recommenders = recommenders;
    }
}
