package com.example.finalproject.UI.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;

public class recieverAgreement extends AppCompatActivity {

    TextView loanDataAndObligation;
    private EditText digitalSignature;
    Button agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_agreement);

        agree = (Button) findViewById(R.id.AgreeAndSendAgreement);
        loanDataAndObligation=findViewById(R.id.loanDataAndObligation);
        digitalSignature = findViewById(R.id.digitalRecieverSignature);

        digitalSignature.addTextChangedListener(TextWatcher);

        //to do- add text with declaration- i comidet to return the amount X with interest X (total X) to X until X
        //loanDataAndObligation.setText("**********************");


        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send to server all loan data and show alert that the system sends the agreement to the giver and wait for the money to come.
                String signature = digitalSignature.getText().toString().trim();
                if(correctFullName(signature)) {
                    Bundle b = getIntent().getExtras();
                    if (b != null) {
                        int loanId = b.getInt("loanId");
                        String giver = b.getString("giver");
                        ViewModel.getInstance().AgreementFromReciever(loanId, giver);
                        alert("Your Agreement has successful send to " + giver + ".\nPlease follow your application mail box often for updates.");
                    } else {
                        Toast.makeText(recieverAgreement.this, "An error accord. Check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private android.text.TextWatcher TextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            String signature = digitalSignature.getText().toString().trim();
            agree.setEnabled(!signature.isEmpty());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String signature = digitalSignature.getText().toString().trim();
            agree.setEnabled(!signature.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean correctFullName(String fullName) {
        return ViewModel.getInstance().checkUserFullName(fullName,MainActivity.getDefaults("userName",recieverAgreement.this));
    }


    public void alert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(recieverAgreement.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //return to home page
                        Intent intent = new Intent(recieverAgreement.this, HomePage.class);
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }

}