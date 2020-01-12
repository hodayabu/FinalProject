package com.example.finalproject.ResponseObjects;
/*
    This Object represent loan request from the pass-each instance is a request that has been saved in DB
    use in: 1. get all request i asked for.
            2. find match for offer loan
 */
public class askedLoans {

    int id;
    String userName;
    int amount;
    String description;
    String video;
    float interestPrecent;


    public askedLoans(int id, String userName, int amount, String description,String video, float interestPrecent) {
        this.id = id;
        this.userName = userName;
        this.amount = amount;
        this.description = description;
        this.video=video;
        this.interestPrecent = interestPrecent;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public String getVideo() {
        return video;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public float getInterestPrecent() {
        return interestPrecent;
    }

    public void setInterestPrecent(float interestPrecent) {
        this.interestPrecent = interestPrecent;
    }





}
