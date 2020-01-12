package com.example.finalproject.UI.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.ResponseObjects.postedLoan;
import com.example.finalproject.ServerRequests.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostedLoans extends menuActivity {

    ListView postedLoansList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_loans);
        postedLoansList=(ListView)findViewById(R.id.postedLoansList);

        final List<postedLoan> list= ViewModel.getInstance().getAllPostedLoans();
        ArrayList<String> fixedPostedLoans=fixList(list);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,fixedPostedLoans);
        postedLoansList.setAdapter(arrayAdapter);


        postedLoansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PostedLoans.this,"item id: "+list.get(position).getId(),Toast.LENGTH_SHORT).show();
                //To-Do Add option for edit posted loan on click.
            }
        });


    }

    private ArrayList<String> fixList(List<postedLoan> list) {
        ArrayList<String> ans=new ArrayList<>();
        int counter=1;
        for(postedLoan postedLoan:list){
            String loan=counter+"   ."+"\nAmount You Offer:   "+postedLoan.getAmount()+"  NIS\nLoan For:   "+postedLoan.getPeriod()+"months\nMinimum Rank Of Borrowers:   "+postedLoan.getRankFilter()+"   Or Higher";
            ans.add(loan);
            counter++;
        }
        return ans;
    }
}
