package com.example.finalproject.ResponseObjects;
/*
    This Object represent new offer of loan- the server get for specific user new offer for loan
    use in: 1. post new loan offer
 */
public class postOffer {
    private int amount;
    private String period;
    private float rankFilter;

    public postOffer(int amount, String period, float rankFilter) {
        this.amount = amount;
        this.period = period;
        this.rankFilter = rankFilter;
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
