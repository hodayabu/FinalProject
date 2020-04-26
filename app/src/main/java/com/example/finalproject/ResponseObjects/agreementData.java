package com.example.finalproject.ResponseObjects;

public class agreementData {

    int agreeId;
    String giver;
    String reciever;
    String expirationDate;
    String description;
    int amount;
    float interest;
    int offerId;
    int requestId;
    boolean is_approved;

    public agreementData(int agreeId, String giver, String receiver, String expirationDate, String description, int amount, float interest, int offerId, int requestId,boolean is_approved) {
        this.agreeId = agreeId;
        this.giver = giver;
        this.reciever = receiver;
        this.expirationDate = expirationDate;
        this.description = description;
        this.amount = amount;
        this.interest = interest;
        this.offerId = offerId;
        this.requestId = requestId;
        this.is_approved=is_approved;
    }


    public int getAgreeId() {
        return agreeId;
    }

    public void setAgreeId(int agreeId) {
        this.agreeId = agreeId;
    }

    public String getGiver() {
        return giver;
    }

    public void setGiver(String giver) {
        this.giver = giver;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }
}
