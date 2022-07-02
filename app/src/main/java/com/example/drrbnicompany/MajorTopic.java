package com.example.drrbnicompany;

import java.util.HashMap;

public class MajorTopic {

    private HashMap<String , String> major ;

    private static MajorTopic instance;

    public static MajorTopic getInstance() {
        if (instance == null) {
            instance = new MajorTopic();
        }
        return instance;
    }

    public MajorTopic() {
        this.major = new HashMap<>();
        setMajor();
    }

    public void setMajor(){
        major.put("تكنولوجيا الوسائط المتعددة" , "multimedia" );
        major.put("البرمجيات وقواعد البيانات" ,"software_and_databases");
        major.put("تصميم وتطوير مواقع الويب" , "website_design_and_development");
        major.put("أمن المعلومات السيبراني" , "cyber_security");
        major.put("علم البيانات والذكاء الإصطناعي" , "Data_science_and_artificial_intelligence");
        major.put("شبكات الحاسوب والإنترنت" , "Computer_networks_and_the_Internet");
        major.put("الهندسة المعمارية" , "architecture");
        major.put("الهندسة المدنية" , "civil_engineering");
        major.put("هندسة التشييد وإدارة المشاريع" , "construction_engineering_and_project_management");
        major.put("هندسة المساحة" , "space_engineering");
        major.put("نظم المعلومات الجغرافية" , "GIS");
        major.put("المحاسبة" , "accounting");
        major.put("إداراة أتمتة المكاتب" , "secretary");
        major.put("التسويق الإلكتروني" , "e_marketing");
        major.put("الإعلام الرقمي" , "digital_media");
        major.put("العلاقات العامة والإعلان" , "public_relations_and_advertising");
    }

    public String getMajorTopic(String major){

        return  this.major.get(major);
    }
}
