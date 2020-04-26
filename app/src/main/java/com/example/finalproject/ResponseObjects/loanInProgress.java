package com.example.finalproject.ResponseObjects;

public class loanInProgress {
    int loanID;
    String giver;
    String reciever;
    String experationDate;
    String description;
    int amount;
    float interest;
//    int requestId;
//    int offerId;

    public loanInProgress(int loanID, String giver, String reciever, String experationDate, int amount, float interest, String description) {
        this.loanID = loanID;
        this.giver = giver;
        this.reciever = reciever;
        this.experationDate = experationDate;
        this.amount = amount;
        this.interest = interest;
        this.description = description;
    }

    public int getLoanID() {
        return loanID;
    }

    public String getDescription() {
        return description;
    }

    public String getGiver() {
        return giver;
    }

    public String getReciever() {
        return reciever;
    }

    public String getExperationDate() {
        return experationDate;
    }

    public int getAmount() {
        return amount;
    }

    public float getInterest() {
        return interest;
    }
}
