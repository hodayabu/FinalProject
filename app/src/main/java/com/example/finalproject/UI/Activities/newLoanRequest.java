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

public class newLoanRequest extends menuActivity implements AdapterView.OnItemSelectedListener{

    TextView calculatedAmount;
    Button postRequest;
    private TextInputLayout textInputDescription;
    int amount=500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_loan_request);

        postRequest=(Button)findViewById(R.id.postRequest);
        textInputDescription = findViewById(R.id.text_input_description);
        calculatedAmount = findViewById(R.id.calculetInterest);

        Spinner spinner = findViewById(R.id.spinner1);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amounts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        postRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInput(textInputDescription.getEditText().getText().toString(),amount)) {
                    postNewLoanRequest(textInputDescription.getEditText().getText().toString(), amount);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        amount=Integer.parseInt(text);
        float interest=ViewModel.getInstance().calculateRank(MainActivity.getDefaults("userName",newLoanRequest.this),amount);
        float total=calculateAmount(interest);
        calculatedAmount.setText("Amount To Return ( "+interest+"% interest) :"+total+" NIS");

    }

    private float calculateAmount(float interest) {
        float precent=interest/100;
        float toAdd=amount*precent;
        return amount+toAdd;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        amount=4000;
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


}
