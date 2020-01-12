package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.finalproject.R;
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
    TextView userName;
    TextView interest;
    TextView rank;
    ImageView pic;
    private DatabaseReference mDatabaseRef;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        loans=(Button)findViewById(R.id.loansIGave);
        offered=(Button)findViewById(R.id.offer);
        askedFor=(Button)findViewById(R.id.askedFor);
        owes=(Button)findViewById(R.id.owes);
        userName=(TextView) findViewById(R.id.welcomeUser);
        interest=(TextView)findViewById(R.id.userData);
        rank=(TextView)findViewById(R.id.interestHomePage);
        pic=(ImageView) findViewById(R.id.homePageImg);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        ratingBar.setRating(Float.parseFloat(MainActivity.getDefaults("rank",HomePage.this)));

        setData();
        setImage();



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
                startActivity(intent);            }
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
        interest.setText("You Will Pay: "+MainActivity.getDefaults("interest",HomePage.this)+"% interest");
        rank.setText("Your rank in the system: "+MainActivity.getDefaults("rank",HomePage.this));
    }
}
