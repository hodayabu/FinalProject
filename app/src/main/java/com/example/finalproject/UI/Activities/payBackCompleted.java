package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.ResponseObjects.mailMsg;
import com.example.finalproject.ServerRequests.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class payBackCompleted extends AppCompatActivity {
    ListView payBackCompleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_back_completed);

        payBackCompleted = (ListView) findViewById(R.id.payBackCompleted);

        final List<mailMsg> list = ViewModel.getInstance().gatAllPayBackCompletedLoans();
        if(list.size()==0){
            Intent intent = new Intent(payBackCompleted.this, noResult.class);
            startActivity(intent);
        }
        ArrayList<String> fixedPostedLoans = fixList(list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fixedPostedLoans);
        payBackCompleted.setAdapter(arrayAdapter);

        payBackCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private ArrayList<String> fixList(List<mailMsg> list) {
        ArrayList<String> ans = new ArrayList<>();
        int counter = 1;
        for (mailMsg mailMsg : list) {
            String loan = counter + "   ." + "\n"+mailMsg.getMsg();
            ans.add(loan);
            counter++;
        }
        return ans;
    }
}
