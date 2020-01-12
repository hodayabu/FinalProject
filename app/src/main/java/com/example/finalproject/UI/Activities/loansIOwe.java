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
import com.example.finalproject.ResponseObjects.gaveAndOweLoans;
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

public class loansIOwe extends menuActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<ImageDisplayItem> mUploads;
    private List<gaveAndOweLoans> usersFromServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans_iowe);


        mRecyclerView = findViewById(R.id.recycler_viewOwe);
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

        for(gaveAndOweLoans gaveAndOweLoans: usersFromServer) {
            Query q = mDatabaseRef.orderByChild("name").equalTo(gaveAndOweLoans.getGiver());
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Upload u = dataSnapshot1.getValue(Upload.class);
                        ImageDisplayItem item=new ImageDisplayItem(createData(gaveAndOweLoans),u.getImageUrl(),gaveAndOweLoans.getGiver());
                        mUploads.add(item);
                    }


                    mAdapter = new ImageAdapter(loansIOwe.this, mUploads);
                    mAdapter.setOnItemClickListener(loansIOwe.this);


                    mRecyclerView.setAdapter(mAdapter);
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }

                private String createData(com.example.finalproject.ResponseObjects.gaveAndOweLoans gaveAndOweLoans) {
                    return "You owe : "+gaveAndOweLoans.getAmount()+" NIS\nTO : "+gaveAndOweLoans.getGiver()+"\nInterest : "+gaveAndOweLoans.getInterest()+"%\nLoan Expiration Date : "+gaveAndOweLoans.getExpirationDate();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(loansIOwe.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

    private void getUsersFromServer() {
        usersFromServer= ViewModel.getInstance().getAllLoansIOwe();
    }


    @Override
    public void onItemClick(int position, ImageDisplayItem i) {
        Intent intent = new Intent(loansIOwe.this, userProfile.class);
        Bundle b = new Bundle();
        b.putString("userName", i.getUserName());
        intent.putExtras(b);
        startActivity(intent);
        finish();

        //Toast.makeText(this,"Clicked on "+position+" with data: "+i.getUserName(),Toast.LENGTH_SHORT).show();
    }

}
