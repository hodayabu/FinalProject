package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

public class Inbox extends AppCompatActivity {

    Button inbox_match;
    Button inbox_finalApprove;
    Button inbox_loanCompleted;
    Button inbox_monthDebts;
    Button inbox_returnsLoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        inbox_match=(Button) findViewById(R.id.inbox_match);
        inbox_finalApprove=(Button)findViewById(R.id.inbox_finalApprove);
        inbox_loanCompleted=(Button)findViewById(R.id.inbox_loanCompleted);
        inbox_monthDebts=(Button)findViewById(R.id.inbox_monthDebts);
        inbox_returnsLoan=(Button)findViewById(R.id.inbox_returnsLoan);

        inbox_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inbox.this, waitForApprove.class);
                startActivity(intent);
            }
        });


        inbox_finalApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inbox.this, giver_payments.class);
                startActivity(intent);
            }
        });


        inbox_loanCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inbox.this, loansCompleted.class);
                startActivity(intent);
            }
        });

        inbox_monthDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inbox.this, monthDebt.class);
                startActivity(intent);
            }
        });

        inbox_returnsLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inbox.this, payBackCompleted.class);
                startActivity(intent);
            }
        });


    }
}
