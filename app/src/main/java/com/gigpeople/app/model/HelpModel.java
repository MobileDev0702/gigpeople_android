package com.gigpeople.app.model;

public class HelpModel {
    int bullet;
    String text;




    public HelpModel(int bullet, String text) {
        this.bullet=bullet;
        this.text=text;

    }


    public int getBullet() {
        return bullet;
    }

    public void setBullet(int bullet) {
        this.bullet = bullet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
