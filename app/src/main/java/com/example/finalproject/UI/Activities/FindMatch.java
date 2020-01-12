package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.ResponseObjects.askedLoans;
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

public class FindMatch extends menuActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<ImageDisplayItem> mUploads;
    private List<askedLoans> usersFromServer;
    private String rankFilter;
    private String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            rankFilter = b.getString("rankFilter");
            amount = b.getString("amount");
        }


        mRecyclerView = findViewById(R.id.recycler_viewMatch);
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

        //setAdapter with new objects



    }

    private void getUrlFromFirebase() {

        for(askedLoans details: usersFromServer) {
            Query q = mDatabaseRef.orderByChild("name").equalTo(details.getUserName());
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Upload u = dataSnapshot1.getValue(Upload.class);
                        ImageDisplayItem item=new ImageDisplayItem(createData(details),u.getImageUrl(),details.getUserName());
                        mUploads.add(item);
                    }


                    mAdapter = new ImageAdapter(FindMatch.this, mUploads);
                    mAdapter.setOnItemClickListener(FindMatch.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }

                private String createData(askedLoans details) {
                    return details.getUserName()+" is asking for "+details.getAmount()+" NIS\n"+"Request Description: "+details.getDescription();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(FindMatch.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

    private void getUsersFromServer() {
        usersFromServer= ViewModel.getInstance().findMatch(rankFilter,amount);
    }


    @Override
    public void onItemClick(int position, ImageDisplayItem i) {
        Intent intent = new Intent(FindMatch.this, userProfile.class);
        Bundle b = new Bundle();
        b.putString("userName", i.getUserName());
        intent.putExtras(b);
        startActivity(intent);
        finish();
        //Toast.makeText(this,"Clicked on "+position+" with data: "+i.getUserName(),Toast.LENGTH_SHORT).show();
    }
}
