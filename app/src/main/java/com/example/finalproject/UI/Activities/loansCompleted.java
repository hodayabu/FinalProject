package com.example.finalproject.UI.Activities;

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

public class loansCompleted extends AppCompatActivity {
    ListView loansCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans_completed);

        loansCompleted = (ListView) findViewById(R.id.loansCompleted);

        final List<mailMsg> list = ViewModel.getInstance().gatAllCompletedLoans();
        ArrayList<String> fixedPostedLoans = fixList(list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fixedPostedLoans);
        loansCompleted.setAdapter(arrayAdapter);

        loansCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(loansCompleted.this, "item id: " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
                //To-Do Add option for edit posted loan on click.
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
