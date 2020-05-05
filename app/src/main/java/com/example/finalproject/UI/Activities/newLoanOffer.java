package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class newLoanOffer extends menuActivity implements AdapterView.OnItemSelectedListener {

    TextView tvProgressLabel;
    Button postOffer;
    private TextInputLayout rankFilter;
    private TextInputLayout period;
    int amount=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_loan_offer);

        postOffer = (Button) findViewById(R.id.postOffer);
        rankFilter = findViewById(R.id.rankFilter);
        period = findViewById(R.id.period);
        tvProgressLabel = findViewById(R.id.progress);
        Spinner spinner = findViewById(R.id.spinner1);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amounts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        postOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validInput(period.getEditText().getText().toString(),rankFilter.getEditText().getText().toString(),amount)) {
                    int loadId=postNewLoanOffer(period.getEditText().getText().toString(),rankFilter.getEditText().getText().toString(),amount);
                    Toast.makeText(newLoanOffer.this,"Offer Posted Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(newLoanOffer.this, FindMatch.class);
                    Bundle b = new Bundle();
                    b.putString("rankFilter",rankFilter.getEditText().getText().toString() );
                    b.putString("amount",amount+"");
                    b.putString("period",period.getEditText().getText().toString());
                    b.putInt("offerLoanId",loadId);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
                else {
                    String input="Please enter valid parameters";
                    Toast.makeText(getApplicationContext(), input, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        amount=Integer.parseInt(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        amount=4000;
    }


    private int postNewLoanOffer(String period,String rankFilter, int amount) {
        return ViewModel.getInstance().postNewOffer(period,rankFilter,amount);
    }

    private boolean validInput(String period,String rankFilter, int progress) {
        if(period.length()==0||rankFilter.length()==0||progress<500||progress>4000){
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








}
