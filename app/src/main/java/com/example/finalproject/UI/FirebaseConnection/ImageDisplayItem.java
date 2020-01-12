package com.example.finalproject.UI.FirebaseConnection;

public class ImageDisplayItem {

    private String data;
    private String userName;
    private String imgUrl;

    public ImageDisplayItem(String data, String imgUrl,String userName) {
        this.data = data;
        this.imgUrl = imgUrl;
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
