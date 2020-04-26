package com.example.finalproject.UI.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

public class paypalTest extends AppCompatActivity {

    Button paypalTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_test);

        paypalTest=(Button)findViewById(R.id.paypalTest);

        paypalTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(ViewModel.getInstance().paypalTest());

            }
        });
    }
}
