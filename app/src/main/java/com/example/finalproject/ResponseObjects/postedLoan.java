package com.example.finalproject.ResponseObjects;
/*
    This Object represent posted loan- the server return for specific user, all the loans he posted
    use in: 1. get all loans i offer
 */
public class postedLoan {
    int id;
    String userName;
    int amount;
    String period;//for how long my loan offer is
    float rankFilter;




    public postedLoan(int id, String userName, int amount, String period, float rankFilter) {
        this.id = id;
        this.userName = userName;
        this.amount = amount;
        this.period = period;
        this.rankFilter = rankFilter;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getRankFilter() {
        return rankFilter;
    }

    public void setRankFilter(float rankFilter) {
        this.rankFilter = rankFilter;
    }




}
