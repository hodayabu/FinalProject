package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.ResponseObjects.reviewData;
import com.example.finalproject.ServerRequests.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class userReviews extends AppCompatActivity {
    ListView reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reviews);
        reviewList = (ListView) findViewById(R.id.reviewList);

        String user="";
        Bundle b = getIntent().getExtras();
        if (b != null) {
            user = b.getString("user");
        }
        final List<reviewData> list = ViewModel.getInstance().getAllReviews(user);
        if(list.size()==0){
            Intent intent = new Intent(userReviews.this, noResult.class);
            startActivity(intent);
        }
        ArrayList<String> fixedPostedLoans = fixList(list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fixedPostedLoans);
        reviewList.setAdapter(arrayAdapter);

        reviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    private ArrayList<String> fixList(List<reviewData> list) {
        ArrayList<String> ans = new ArrayList<>();
        int counter = 1;
        for (reviewData reviewData : list) {
            String review=reviewData.getReviewer()+" said "+ reviewData.getReview()+". \nScore: "+reviewData.getReview_score();
            ans.add(review);
            counter++;
        }
        return ans;
    }



}
