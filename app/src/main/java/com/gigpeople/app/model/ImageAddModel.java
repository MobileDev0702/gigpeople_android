package com.gigpeople.app.model;

import android.graphics.Bitmap;

public class ImageAddModel {
    Bitmap imageBitmap;

    String status;

    public ImageAddModel(Bitmap imageBitmap, String status) {
        this.imageBitmap = imageBitmap;
        this.status = status;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
