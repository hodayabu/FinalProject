package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;

public class payPal extends menuActivity {
    //TO-DO update the textView with loan details
    Button payPalbutton;
    int amount;
    int loanId;
    boolean payBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);


        payPalbutton=(Button)findViewById(R.id.payPalButton);

        payPalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = getIntent().getExtras();
                if(b != null) {
                    amount = b.getInt("amount");
                    loanId=b.getInt("loanId");
                    payBack=b.getBoolean("payBack");
                }
                //server send url for payment at this amount
                String url;
                if(payBack){
                    url=ViewModel.getInstance().paypalBack(amount,loanId);
                }
                else{ url=ViewModel.getInstance().paypalTest(amount,loanId);}
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}
