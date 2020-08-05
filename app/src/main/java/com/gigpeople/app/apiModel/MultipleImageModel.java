package com.gigpeople.app.apiModel;

public class MultipleImageModel {
  String status,url,image,type,thumbnail;




    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public MultipleImageModel(String image, String url, String type, String thumbnail) {
        this.image = image;
        this.status=status;
        this.url=url;
        this.type=type;
        this.thumbnail=thumbnail;



    }


    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


