package com.gigpeople.app.model;

public class ImageVideoModel {

    String file_url,thumb_url,type;

    public ImageVideoModel(String file_url, String thumb_url, String type) {
        this.file_url = file_url;
        this.thumb_url = thumb_url;
        this.type = type;
    }

    public String getFile_url() {
        return file_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public String getType() {
        return type;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public void setType(String type) {
        this.type = type;
    }
}
