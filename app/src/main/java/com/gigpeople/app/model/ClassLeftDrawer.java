package com.gigpeople.app.model;

public class ClassLeftDrawer {

    String menu_name;
    int menu_img;

    public ClassLeftDrawer(Integer menu_img, String menu_name) {
        this.menu_name = menu_name;
        this.menu_img = menu_img;
    }


    public String getMenu_name() {
        return menu_name;
    }

    public int getMenu_img() {
        return menu_img;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public void setMenu_img(int menu_img) {
        this.menu_img = menu_img;
    }

}
