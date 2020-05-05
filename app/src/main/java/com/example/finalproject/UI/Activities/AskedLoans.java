package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.ResponseObjects.askedLoans;
import com.example.finalproject.ServerRequests.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AskedLoans extends menuActivity {
    ListView askedLoansList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asked_loans);
        askedLoansList = (ListView) findViewById(R.id.askedLoansList);

        final List<askedLoans> list = ViewModel.getInstance().getAllAskedLoans();
        if(list.size()==0){
            Intent intent = new Intent(AskedLoans.this, noResult.class);
            startActivity(intent);
        }
        ArrayList<String> fixedPostedLoans = fixList(list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fixedPostedLoans);
        askedLoansList.setAdapter(arrayAdapter);

        askedLoansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AskedLoans.this, "item id: " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
                //To-Do Add option for edit posted loan on click.
            }
        });


    }

    private ArrayList<String> fixList(List<askedLoans> list) {
        ArrayList<String> ans = new ArrayList<>();
        int counter = 1;
        for (askedLoans askedLoans : list) {
            String loan = counter + "   ." + "\nAmount You Asked For:   " + askedLoans.getAmount() + "  NIS\nReason:   " + askedLoans.getDescription() + "\nYour Interest Percent:   " + askedLoans.getInterestPrecent()+"%";
            ans.add(loan);
            counter++;
        }
        return ans;
    }
}
