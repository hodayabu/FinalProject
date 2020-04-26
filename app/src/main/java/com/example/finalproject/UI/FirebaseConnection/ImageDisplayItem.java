package com.example.finalproject.UI.FirebaseConnection;

public class ImageDisplayItem {

    private String data;
    private String userName;
    private String imgUrl;
    private String description;
    private String exparationDate;
    private float interest;
    private int amount;
    private int progressId;
    private int requestId;

    //use in loans i gave or owe
    public ImageDisplayItem(String data, String imgUrl,String userName) {
        this.data = data;
        this.imgUrl = imgUrl;
        this.userName=userName;
    }

    //use in find match
    public ImageDisplayItem(String data, String imgUrl,String userName,String description,float interest,int amount,int requestId) {
        this.data = data;
        this.imgUrl = imgUrl;
        this.userName=userName;
        this.description=description;
        this.interest=interest;
        this.amount=amount;
        this.requestId=requestId;
    }

    //use in wait for approve
    public ImageDisplayItem(String data, String imgUrl,String userName,String description,float interest,int amount,String exparationDate,int progressId) {
        this.data = data;
        this.imgUrl = imgUrl;
        this.userName=userName;
        this.description=description;
        this.interest=interest;
        this.amount=amount;
        this.exparationDate=exparationDate;
        this.progressId=progressId;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getUserName() {
        return userName;
    }

    public int getProgressId() {
        return progressId;
    }

    public String getDescription() {
        return description;
    }

    public String getExparationDate() {
        return exparationDate;
    }

    public float getInterest() {
        return interest;
    }
    public int getAmount() {
        return amount;
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
