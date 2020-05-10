package com.example.finalproject.ResponseObjects;

public class postRequest {
/*
    This Object represent new request for loan- the server get for specific user new request for loan
    use in: 1. post new loan request
 */

    int amount;
    String description;
    String video;
    float rank;


    public postRequest(int amount, String description, String video, float rank) {
        this.amount = amount;
        this.description = description;
        this.video = video;
        this.rank = rank;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }




}
