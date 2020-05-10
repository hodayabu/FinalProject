package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;

public class loanDetails extends menuActivity {

    TextView giver;
    TextView description;
    TextView amount;
    TextView interest;
    TextView exparationDate;
    Button exceptLoan;
    int loanId;
    float interest_num;
    int amount_num;
    String giverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);

        giver = findViewById(R.id.giver);
        description = findViewById(R.id.description);
        amount = findViewById(R.id.amount);
        interest = findViewById(R.id.interest);
        exparationDate = findViewById(R.id.exparationDate);
        exceptLoan = findViewById(R.id.exceptLoan);


        Bundle b = getIntent().getExtras();
        if(b != null) {
            giverName=b.getString("userName");
            giver.setText(b.getString("userName")+" offered you a loan");
            exparationDate.setText("The Loan Is Until : "+ b.getString("exparationDate"));
            description.setText( "Your Request Description :"+b.getString("description"));
            amount.setText("Loan Amount :"+ b.getInt("amount"));
            interest.setText("Interest Percent :"+b.getFloat("interest"));
            loanId=b.getInt("loanId");
            interest_num=b.getFloat("interest");
            amount_num=b.getInt("amount");

        }

        exceptLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loanDetails.this, recieverAgreement.class);
                Bundle b = new Bundle();
                //send all loan data to reciever agreement--put 1 at boolean field on this loanId record
                b.putString("giver",giverName);
                b.putInt("loanId", loanId);
                b.putString("exparationDate", exparationDate.getText().toString());
                b.putFloat("interest", interest_num);
                b.putInt("amount",amount_num);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }
}
