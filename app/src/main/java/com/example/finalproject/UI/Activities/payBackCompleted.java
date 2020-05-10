package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.ResponseObjects.mailMsg;
import com.example.finalproject.ServerRequests.ViewModel;
import com.example.finalproject.UI.FirebaseConnection.ImageAdapter;
import com.example.finalproject.UI.FirebaseConnection.ImageDisplayItem;
import com.example.finalproject.UI.FirebaseConnection.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class payBackCompleted extends menuActivity  implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<ImageDisplayItem> mUploads;
    private List<mailMsg> usersFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_back_completed);



        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        usersFromServer=new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        //TO-DO get data of users from server
        getUsersFromServer();

        //TO-DO bring from DB the urls of this users (equalto)
        getUrlFromFirebase();





//        payBackCompleted = (ListView) findViewById(R.id.payBackCompleted);
//
//        final List<mailMsg> list = ViewModel.getInstance().gatAllPayBackCompletedLoans();
//        if(list.size()==0){
//            Intent intent = new Intent(payBackCompleted.this, noResult.class);
//            startActivity(intent);
//        }
//        ArrayList<String> fixedPostedLoans = fixList(list);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fixedPostedLoans);
//        payBackCompleted.setAdapter(arrayAdapter);
//
//        payBackCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//            }
//        });
    }

//    private ArrayList<String> fixList(List<mailMsg> list) {
//        ArrayList<String> ans = new ArrayList<>();
//        int counter = 1;
//        for (mailMsg mailMsg : list) {
//            String loan = counter + "   ." + "\n"+mailMsg.getMsg();
//            ans.add(loan);
//            counter++;
//        }
//        return ans;
//    }



    private void getUrlFromFirebase() {

        for(mailMsg msg: usersFromServer) {
            Query q = mDatabaseRef.orderByChild("name").equalTo(msg.getPartner());
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Upload u = dataSnapshot1.getValue(Upload.class);
                        ImageDisplayItem item=new ImageDisplayItem(msg.getMsg()+"\nClick here to rank the user!",u.getImageUrl(),msg.getPartner());
                        mUploads.add(item);
                    }


                    mAdapter = new ImageAdapter(payBackCompleted.this, mUploads);
                    mAdapter.setOnItemClickListener(payBackCompleted.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

    private void getUsersFromServer() {
        usersFromServer=ViewModel.getInstance().gatAllPayBackCompletedLoans();
        if(usersFromServer.size()==0){
            Intent intent = new Intent(payBackCompleted.this, noResult.class);
            startActivity(intent);
        }
    }


    @Override
    public void onItemClick(int position, ImageDisplayItem i) {
        Intent intent = new Intent(payBackCompleted.this, reviewUserAfterLoan.class);
        Bundle b = new Bundle();
        b.putString("userName", i.getUserName());
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}
