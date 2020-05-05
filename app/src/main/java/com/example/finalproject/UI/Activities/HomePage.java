package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;
import com.example.finalproject.UI.FirebaseConnection.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomePage extends menuActivity {
    Button loans;
    Button offered;
    Button askedFor;
    Button owes;
    Button newOffer;
    Button mail;
    Button newRequest;
    TextView userName;
    ImageView pic;
    private DatabaseReference mDatabaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        loans=(Button)findViewById(R.id.loansIGave);
        offered=(Button)findViewById(R.id.offer);
        newOffer=(Button)findViewById(R.id.newOffer);
        newRequest=(Button)findViewById(R.id.newRequest);
        askedFor=(Button)findViewById(R.id.askedFor);
        owes=(Button)findViewById(R.id.owes);
        userName=(TextView) findViewById(R.id.welcomeUser);
        pic=(ImageView) findViewById(R.id.homePageImg);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mail = findViewById(R.id.mailButton);

        setData();
        setImage();
        mail.setEnabled(chaeckMailBox());// notification if mailBox full

        //resize loans and owe according to the user balance
        balanceLoans();
        balanceOwe();

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Inbox.class);
                startActivity(intent);
            }
        });

        owes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, loansIOwe.class);
                startActivity(intent);
            }
        });

        loans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, loansIGave.class);
                startActivity(intent);
                }
        });

        offered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, PostedLoans.class);
                startActivity(intent);

            }
        });

        askedFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AskedLoans.class);
                startActivity(intent);

            }
        });

        newOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, newLoanOffer.class);
                startActivity(intent);

            }
        });

        newRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, newLoanRequest.class);
                startActivity(intent);
            }
        });


        Uri data = this.getIntent().getData();
        if (data != null && data.isHierarchical()) {
            String uri = this.getIntent().getDataString();
            Log.i("MyApp", "Deep link clicked " + uri);
        }

    }

    private void balanceOwe() {
        String strTotalOwe=MainActivity.getDefaults("totalOwes",HomePage.this);
        int totalOwe=Integer.parseInt(strTotalOwe);
        int newWidth= calculateSize(totalOwe);

        ViewGroup.LayoutParams params = owes.getLayoutParams();
        params.width = newWidth;
        owes.setLayoutParams(params);
        owes.setText("your total debts:   "+totalOwe);

    }

    private void balanceLoans() {

        String strTotalLoans=MainActivity.getDefaults("totalLoans",HomePage.this);
        int totalLoans=Integer.parseInt(strTotalLoans);
        int newWidth= calculateSize(totalLoans);

        ViewGroup.LayoutParams params = loans.getLayoutParams();
        params.width = newWidth;
        loans.setLayoutParams(params);
        loans.setText("your total loans:   "+totalLoans);
    }

    private int calculateSize(int total) {


        if(total>=4000) return 900; //max size
        if(total>=3000) return 750;
        if(total>=2000) return 550;
        if(total>=1000) return 400;

        else return 200;//min size
    }

    private boolean chaeckMailBox() {
        return ViewModel.getInstance().checkMailBox();
    }

    private void setImage() {
        Query q= mDatabaseRef.orderByChild("name").equalTo(MainActivity.getDefaults("userName",HomePage.this)+"");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Upload u= dataSnapshot1.getValue(Upload.class);
                    Picasso.with(HomePage.this)
                            .load(u.getImageUrl())
                            .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomePage.this,"Couldn't load image",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData() {
        userName.setText("Hello "+MainActivity.getDefaults("userName",HomePage.this));

    }
}
