package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class newLoanOffer extends menuActivity {

    TextView tvProgressLabel;
    Button postOffer;
    private TextInputLayout rankFilter;
    private TextInputLayout period;
    int progress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_loan_offer);

        SeekBar seekBar = findViewById(R.id.seekBarOffer);
        postOffer = (Button) findViewById(R.id.postOffer);
        rankFilter = findViewById(R.id.rankFilter);
        period = findViewById(R.id.period);

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        progress = seekBar.getProgress();
        tvProgressLabel = findViewById(R.id.progress);
        tvProgressLabel.setText("Amount: " + progress);

        postOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = seekBar.getProgress();
                if(validInput(period.getEditText().getText().toString(),rankFilter.getEditText().getText().toString(),progress)) {
                    postNewLoanOffer(period.getEditText().getText().toString(),rankFilter.getEditText().getText().toString(),progress);
                    Toast.makeText(newLoanOffer.this,"Offer Posted Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(newLoanOffer.this, FindMatch.class);
                    Bundle b = new Bundle();
                    b.putString("rankFilter",rankFilter.getEditText().getText().toString() );
                    b.putString("amount",progress+"");
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
                else {
                    String input="Please enter valid amount and loan reason";
                    Toast.makeText(getApplicationContext(), input, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void postNewLoanOffer(String period,String rankFilter, int amount) {
        ViewModel.getInstance().postNewOffer(period,rankFilter,amount);
    }

    private boolean validInput(String period,String rankFilter, int progress) {
        if(period.length()==0||rankFilter.length()==0||progress<500||progress>5000){
            return false;
        }
        try {
            if (Integer.parseInt(period) < 1 || Integer.parseInt(period) > 12||Float.parseFloat(rankFilter) < 0 || Float.parseFloat(rankFilter) > 5) {
                Toast.makeText(newLoanOffer.this,"Please Enter Valid Inputs",Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            Toast.makeText(newLoanOffer.this,"Please Enter Valid Inputs",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvProgressLabel.setText("Amount: " + progress+" NIS");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //TO-DO delete if there is no use!
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //TO-DO add here the calculation of the RIBIT for this user by rank
            // called after the user finishes moving the SeekBar
        }
    };







}
