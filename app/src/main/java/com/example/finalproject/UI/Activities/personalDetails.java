package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;

public class personalDetails extends menuActivity {

    RadioGroup radioGroupIncome;
    RadioGroup radioGroupAge;
    RadioGroup radioGroupParents;
    RadioGroup radioGroupMinus;
    RadioGroup radioGroupDebts;
    RadioGroup radioGroupFacebook;
    RadioGroup radioGroupFriends;
    RadioGroup radioGroupInstagram;
    RadioButton radioButton;
    Button confirmPersonalDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        radioGroupIncome = findViewById(R.id.radioGroupIncome);
        radioGroupAge = findViewById(R.id.radioGroupAge);
        radioGroupParents = findViewById(R.id.radioGroupParents);
        radioGroupMinus = findViewById(R.id.radioGroupMinus);
        radioGroupDebts = findViewById(R.id.radioGroupDebts);
        radioGroupFacebook = findViewById(R.id.radioGroupFacebook);
        radioGroupFriends = findViewById(R.id.radioGroupfriends);
        radioGroupInstagram = findViewById(R.id.radioGroupInstagram);

        confirmPersonalDetails = findViewById(R.id.submit_personal_details);
        confirmPersonalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int income = incomeID(extractText (radioGroupIncome));
                int age = ageID(extractText (radioGroupAge));
                int parents = parentsID(extractText (radioGroupParents));
                int minus  = minusID(extractText (radioGroupMinus));
                int debts = debtsID(extractText (radioGroupDebts));
                int facebook = facebookID(extractText (radioGroupFacebook));
                int friends = friendsID(extractText (radioGroupFriends));
                int instagram = InstagramID(extractText (radioGroupInstagram));

                ViewModel.getInstance().personalDetails(income,age,parents,minus,debts,facebook,friends,instagram);
                Toast.makeText(personalDetails.this, "Details accepted in the system", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(personalDetails.this, newLoanRequest.class);
                startActivity(intent);
            }

            private int incomeID(String extractText) {
                if(extractText.equals("No Income")) return 0;
                if(extractText.equals("Up to 1,500NIS")) return 1;
                if(extractText.equals("Up to 3,000NIS")) return 2;
                return 3;
            }

            private int ageID(String extractText) {
                if(extractText.equals("0-20")) return 0;
                if(extractText.equals("21-25")) return 1;
                if(extractText.equals("25-30")) return 2;
                return 3;
            }

            private int parentsID(String extractText) {
                if(extractText.equals("No Support")) return 0;
                if(extractText.equals("Up to 1,500NIS")) return 1;
                if(extractText.equals("Up to 3,000NIS")) return 2;
                return 3;
            }

            private int minusID(String extractText) {
                if (extractText.equals("No")) return 0;
                return 1;
            }

            private int debtsID(String extractText) {
                if (extractText.equals("No")) return 0;
                return 1;
            }

            private int facebookID(String extractText) {
                if (extractText.equals("Not Active")) return 0;
                if (extractText.equals("Little Active")) return 1;
                return 2;
            }

            private int friendsID(String extractText) {
                if (extractText.equals("No")) return 0;
                return 1;
            }

            private int InstagramID(String extractText) {
                if (extractText.equals("No")) return 0;
                return 1;
            }
        });
    }

    private String extractText(RadioGroup radioGroup) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        return radioButton.getText().toString();
    }


    public void checkButton(View v) {
        int radioId = radioGroupIncome.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}
