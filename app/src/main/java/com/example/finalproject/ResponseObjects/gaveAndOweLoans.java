package com.example.finalproject.ResponseObjects;
/*
    This Object represent loan object of the server.
    use in: 1. get all loans i asked for.
            2. get all loans i gave.
            3. month debts.
 */
public class gaveAndOweLoans {

    int id;
    String giver;
    String reciever;
    String experationDate;
    String description;
    int amount;
    float interest;


    public gaveAndOweLoans(int loanID, String giver, String receiver,String description, String expirationDate,int amount, float interest) {
        this.id = loanID;
        this.giver = giver;
        this.reciever = receiver;
        this.experationDate = expirationDate;
        this.description = description;
        this.amount=amount;
        this.interest = interest;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getLoanID() {
        return id;
    }

    public void setLoanID(int loanID) {
        this.id = loanID;
    }

    public String getGiver() {
        return giver;
    }

    public void setGiver(String giver) {
        this.giver = giver;
    }

    public String getReceiver() {
        return reciever;
    }

    public void setReceiver(String receiver) {
        this.reciever = receiver;
    }

    public String getExpirationDate() {
        return experationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.experationDate = expirationDate;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
