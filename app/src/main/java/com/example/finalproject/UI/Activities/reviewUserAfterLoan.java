package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class reviewUserAfterLoan extends AppCompatActivity {

    String currentUserName;
    Button submitReview;
    TextView review_score_bar;
    RatingBar ratingBarReview;
    TextInputLayout review;
    float rateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_user_after_loan);

        submitReview = findViewById(R.id.submit_review);
        review_score_bar = findViewById(R.id.review_score_bar);
        ratingBarReview = findViewById(R.id.ratingBarReview);
        review = findViewById(R.id.review);

       Bundle b = getIntent().getExtras();
        if(b != null)
            currentUserName = b.getString("userName");

        ratingBarReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue= ratingBar.getRating();
                review_score_bar.setText("Score: "+rateValue);
            }
        });

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInput()){
                    ViewModel.getInstance().sendReview(currentUserName,review.getEditText().getText().toString(),rateValue);
                    Intent intent = new Intent(reviewUserAfterLoan.this, HomePage.class);
                    startActivity(intent);
                }
            }

            private boolean validInput() {
                if(!review.getEditText().getText().toString().equals("")) return true;
                return false;
            }
        });

    }
}
