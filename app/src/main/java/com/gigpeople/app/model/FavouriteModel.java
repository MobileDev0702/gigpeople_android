package com.gigpeople.app.model;

public class FavouriteModel {
    String status,rating;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public FavouriteModel(String status, String rating) {
        this.status=status;
        this.rating=rating;

    }




}
