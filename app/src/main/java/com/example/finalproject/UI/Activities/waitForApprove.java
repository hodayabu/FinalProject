package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.ResponseObjects.agreementData;
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

public class waitForApprove extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<ImageDisplayItem> mUploads;
    private List<agreementData> usersFromServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_approve);

        mRecyclerView = findViewById(R.id.recycler_viewWaitForApprove);
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

        for(agreementData loanInProgress: usersFromServer) {
            Query q = mDatabaseRef.orderByChild("name").equalTo(loanInProgress.getGiver());
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Upload u = dataSnapshot1.getValue(Upload.class);
                        //********************************
                        //********************************
                        //********************************

                        ImageDisplayItem item=new ImageDisplayItem(createData(loanInProgress),u.getImageUrl(),loanInProgress.getGiver(),loanInProgress.getDescription(),loanInProgress.getInterest(),loanInProgress.getAmount(),loanInProgress.getExpirationDate(),loanInProgress.getAgreeId());
                        mUploads.add(item);
                    }


                    mAdapter = new ImageAdapter(waitForApprove.this, mUploads);
                    mAdapter.setOnItemClickListener(waitForApprove.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }

                private String createData(com.example.finalproject.ResponseObjects.agreementData loanInProgress) {
                    //********************************
                    //********************************
                    //********************************
                    //********************************
                    return loanInProgress.getGiver()+" want to loan you "+loanInProgress.getAmount()+" as you requested for "+loanInProgress.getDescription()+".\nYour interest for this loan is "+loanInProgress.getInterest()+".\nClick here.";
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(waitForApprove.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

    private void getUsersFromServer() {
        usersFromServer= ViewModel.getInstance().getAllwaitingMsg();
        if(usersFromServer.size()==0){
            Intent intent = new Intent(waitForApprove.this, noResult.class);
            startActivity(intent);
        }
    }


    @Override
    public void onItemClick(int position, ImageDisplayItem i) {
        Intent intent = new Intent(waitForApprove.this, loanDetails.class);
        Bundle b = new Bundle();
        b.putString("userName", i.getUserName());
        b.putInt("amount",i.getAmount() );
        //the id from table approve progress
        b.putInt("loanId",i.getProgressId() );
        b.putString("exparationDate", i.getExparationDate());
        b.putString("description", i.getDescription());
        b.putFloat("interest", i.getInterest());
        intent.putExtras(b);
        startActivity(intent);
        finish();
        //Toast.makeText(this,"Clicked on "+position+" with data: "+i.getUserName(),Toast.LENGTH_SHORT).show();
    }
}
