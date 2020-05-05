package com.example.finalproject.UI.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;

public class giverAgreement extends menuActivity {


    Button AgreeAndSendOffer;
    private EditText digitalSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giver_agreement);

        AgreeAndSendOffer = (Button) findViewById(R.id.AgreeAndSendOffer);
        digitalSignature = findViewById(R.id.digitalGiverSignature);

        digitalSignature.addTextChangedListener(TextWatcher);

        AgreeAndSendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send to server all loan data and show alert that the system sends the offer to the reciever and wait for its respons.
                String signature = digitalSignature.getText().toString().trim();
                if(correctFullName(signature)) {
                    Bundle b = getIntent().getExtras();
                    if (b != null) {
                        String currentUserName = b.getString("userName");
                        String period = b.getString("period");
                        String description = b.getString("description");
                        int amount = b.getInt("amount");
                        float interest = b.getFloat("interest");
                        String myUser = MainActivity.getDefaults("userName", giverAgreement.this);
                        int offerLoanId = b.getInt("offerLoanId");
                        int requestLoanId = b.getInt("requestLoanId");
                        ViewModel.getInstance().AgreementFromGiver(myUser, currentUserName, period, description, amount, interest, offerLoanId, requestLoanId);
                        alert("Your offer has successful send to " + currentUserName + ".\nPlease follow your application mail box often for updates.");
                    } else {
                        Toast.makeText(giverAgreement.this, "An error accord. Check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private TextWatcher TextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            String signature = digitalSignature.getText().toString().trim();
            AgreeAndSendOffer.setEnabled(!signature.isEmpty());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String signature = digitalSignature.getText().toString().trim();
            AgreeAndSendOffer.setEnabled(!signature.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean correctFullName(String fullName) {
        return ViewModel.getInstance().checkUserFullName(fullName,MainActivity.getDefaults("userName",giverAgreement.this));
    }


    public void alert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(giverAgreement.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //return to home page
                        Intent intent = new Intent(giverAgreement.this, HomePage.class);
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }
    
}
