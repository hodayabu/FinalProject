package com.example.finalproject.ResponseObjects;

public class reviewData {

    String reviewer;
    String review;
    String review_score;

    public reviewData(String reviewer, String review, String review_score) {
        this.reviewer = reviewer;
        this.review = review;
        this.review_score = review_score;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview_score() {
        return review_score;
    }

    public void setReview_score(String review_score) {
        this.review_score = review_score;
    }
}
