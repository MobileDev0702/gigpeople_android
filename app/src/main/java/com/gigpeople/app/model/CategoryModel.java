package com.gigpeople.app.model;

public class CategoryModel {
    int profile;
    String category,subcategory,status;
    public CategoryModel(String category, String subcategory,String status) {
        this.category=category;
        this.status=status;
        this.subcategory=subcategory;

    }



    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public String getSubcategory() {
        return subcategory;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}
