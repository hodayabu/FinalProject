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
    private String period;
    private String amount;
    private int offerLoanId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            rankFilter = b.getString("rankFilter");
            amount = b.getString("amount");
            period = b.getString("period");
            offerLoanId = b.getInt("offerLoanId");
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




    }

    private void getUrlFromFirebase() {

        for(askedLoans details: usersFromServer) {
            Query q = mDatabaseRef.orderByChild("name").equalTo(details.getUserName());
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Upload u = dataSnapshot1.getValue(Upload.class);
                        //uniqe use of object imageDisplayItem- add the data of the loaner in order to use it in case of a match.
                        ImageDisplayItem item=new ImageDisplayItem(createData(details),u.getImageUrl(),details.getUserName(),details.getDescription(),details.getInterestPrecent(),details.getAmount(),details.getId());
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
        //the server finds potential users who need this amount **(more or less)**
        usersFromServer= ViewModel.getInstance().findMatch(rankFilter,amount);
        if(usersFromServer.size()==0){
            Intent intent = new Intent(FindMatch.this, noResult.class);
            startActivity(intent);
        }
    }


    @Override
    public void onItemClick(int position, ImageDisplayItem i) {

        //01/04/2020-- Open new page of profile with button of send offer. the button direct to page giverAgreement
        //
        Intent intent = new Intent(FindMatch.this, MatchUserProfile.class);
        Bundle b = new Bundle();
        b.putString("userName", i.getUserName());
        b.putInt("amount",i.getAmount() );
        b.putString("period", period);
        b.putString("description", i.getDescription());
        b.putFloat("interest", i.getInterest());
        b.putInt("offerLoanId", offerLoanId);
        b.putInt("requestLoanId", i.getRequestId());
        System.out.println("***************************************");
        System.out.println("id Of request got findmatch screen:    "+i.getRequestId());
        System.out.println("***************************************");

        System.out.println("***************************************");
        System.out.println("id Of offer got findmatch screen:    "+offerLoanId);
        System.out.println("***************************************");
        intent.putExtras(b);
        startActivity(intent);
        finish();
        //Toast.makeText(this,"Clicked on "+position+" with data: "+i.getUserName(),Toast.LENGTH_SHORT).show();
    }
}
