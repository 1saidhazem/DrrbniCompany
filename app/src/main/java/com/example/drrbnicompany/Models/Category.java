package com.example.drrbnicompany.Models;

public class Category {
    private String category_Id,image,name;

    public Category() {}

    public Category(String category_Id, String image, String name) {
        this.category_Id = category_Id;
        this.image = image;
        this.name = name;
    }

    public String getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(String category_Id) {
        this.category_Id = category_Id;
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
