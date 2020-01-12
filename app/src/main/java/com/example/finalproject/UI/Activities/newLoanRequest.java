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

public class newLoanRequest extends menuActivity {

    TextView tvProgressLabel;
    TextView calculatedAmount;
    Button postRequest;
    private TextInputLayout textInputDescription;
    int progress=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_loan_request);

    // set a change listener on the SeekBar
        SeekBar seekBar = findViewById(R.id.seekBar);
        postRequest=(Button)findViewById(R.id.postRequest);
        textInputDescription = findViewById(R.id.text_input_description);
        calculatedAmount = findViewById(R.id.calculetInterest);

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        progress = seekBar.getProgress();
        tvProgressLabel = findViewById(R.id.progress);
        tvProgressLabel.setText("Amount: " + progress);




        postRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = seekBar.getProgress();
                if(validInput(textInputDescription.getEditText().getText().toString(),progress)) {
                    postNewLoanRequest(textInputDescription.getEditText().getText().toString(), progress);
                    Toast.makeText(newLoanRequest.this,"Request Posted Successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(newLoanRequest.this, HomePage.class);
                    startActivity(intent);
                }
                else {
                    String input="Please enter valid amount and loan reason";
                    Toast.makeText(getApplicationContext(), input, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void postNewLoanRequest(String desc, int amount) {
        ViewModel.getInstance().postNewRequest(desc,amount);
    }

    private boolean validInput(String desc, int progress) {
        if(desc.length()==0||progress<500||progress>5000){
            return false;
        }
        return true;
    }


    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvProgressLabel.setText("I Need: " + progress+" NIS");
            calculatedAmount.setText("Amount To Return ( "+MainActivity.getDefaults("interest",newLoanRequest.this)+"% interest) :"+calculateAmount(progress)+" NIS");
        }

        private String calculateAmount(int amount) {
            String interest= MainActivity.getDefaults("interest",newLoanRequest.this);
            float ans=amount*((Float.parseFloat(interest))/100);
            ans+=amount;
            return ans+"";
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
