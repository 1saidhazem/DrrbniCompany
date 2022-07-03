package com.example.drrbnicompany;

import java.util.HashMap;

public class SpinnerPosition {

    private HashMap<String , Integer> major ;
    private HashMap<String , Integer> category ;
    private HashMap<String , Integer> governorate ;

    public SpinnerPosition() {
        this.major = new HashMap<>();
        this.category = new HashMap<>();
        this.governorate = new HashMap<>();
        setMajor();
        setGovernorate();
        setCategory();
    }

    public void setMajor(){
        major.put("تكنولوجيا الوسائط المتعددة" , 1);
        major.put("البرمجيات وقواعد البيانات" , 2);
        major.put("تصميم وتطوير مواقع الويب" , 3);
        major.put("أمن المعلومات السيبراني" , 4);
        major.put("علم البيانات والذكاء الإصطناعي" , 5);
        major.put("شبكات الحاسوب والإنترنت" , 6);
        major.put("الهندسة المعمارية" , 7);
        major.put("الهندسة المدنية" , 8);
        major.put("هندسة التشييد وإدارة المشاريع" , 9);
        major.put("هندسة المساحة" , 10);
        major.put("نظم المعلومات الجغرافية" , 11);
        major.put("المحاسبة" , 12);
        major.put("إداراة أتمتة المكاتب" , 13);
        major.put("التسويق الإلكتروني" , 14);
        major.put("الإعلام الرقمي" , 15);
        major.put("العلاقات العامة والإعلان" , 16);
    }

    public int getMajorPosition(String major){
        return  this.major.get(major);
    }

    public void setGovernorate(){
        governorate.put("شمال غزة" , 1);
        governorate.put("غزة" , 2);
        governorate.put("الوسطى" , 3);
        governorate.put("خانيونس" , 4);
        governorate.put("رفح" , 5);
    }

    public int getGovernoratePosition(String governorate){
        return  this.governorate.get(governorate);
    }


    public void setCategory(){
        category.put("تكنولوجيا المعلومات" , 1);
        category.put("المحاسبة" , 2);
        category.put("الصحافة والاعلام" , 3);
        category.put("الهندسة" , 4);
        category.put("التصميم والديكور" , 5);
        category.put("التسويق" , 6);
        category.put("البلديات" , 7);
    }


    public int getCategoryPosition(String category){

        return  this.category.get(category);
    }
}
