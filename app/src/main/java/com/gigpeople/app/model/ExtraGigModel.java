package com.gigpeople.app.model;

public class ExtraGigModel {
    String title,cost,desc,days;

    public String getTitle() {
        return title;
    }

    public String getCost() {
        return cost;
    }

    public ExtraGigModel(String title, String cost,String desc,String days) {
        this.title = title;
        this.cost = cost;
        this.desc=desc;
        this.days=days;

    }


    public String getDesc() {
        return desc;
    }

    public String getDays() {
        return days;
    }
}
