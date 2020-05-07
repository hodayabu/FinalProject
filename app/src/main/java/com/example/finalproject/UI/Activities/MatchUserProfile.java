package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.ResponseObjects.userInfo;
import com.example.finalproject.ServerRequests.ViewModel;
import com.example.finalproject.UI.FirebaseConnection.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MatchUserProfile extends AppCompatActivity {

    TextView name;
    TextView facebook;
    TextView phone;
    TextView mail;
    TextView university;
    ImageView profilePic;
    String currentUserName;
    private DatabaseReference mDatabaseRef;
    String facebookUrl;
    Button sendOffer;
    int offerLoanId;
    int requestLoanId;
    Button review;

    //data type to pass for giver agreement
    private String period;
    private String description;
    //this is the amount that the user asked for!! not the one that offered.
    private int amount;
    private float interest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_user_profile);

        name=(TextView)findViewById(R.id.MName);
        sendOffer=(Button) findViewById(R.id.sendFinalOffer);
        review=(Button) findViewById(R.id.show_reviews);
        facebook=(TextView)findViewById(R.id.Mfacebook);
        phone=(TextView)findViewById(R.id.Mphone);
        mail=(TextView)findViewById(R.id.Mmail);
        university=(TextView)findViewById(R.id.Muniversity);
        profilePic=(ImageView)findViewById(R.id.MprofileImg) ;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        //get the potential user data--from screen FindMatch
        Bundle b = getIntent().getExtras();
        if(b != null) {
            currentUserName = b.getString("userName");
            period = b.getString("period");
            description = b.getString("description");
            amount = b.getInt("amount");
            interest = b.getFloat("interest");
            offerLoanId=b.getInt("offerLoanId");
            requestLoanId=b.getInt("requestLoanId");
        }
        loadImg();
        loadInfo();

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MatchUserProfile.this, userReviews.class);
                Bundle b = new Bundle();
                b.putString("user",currentUserName );
                startActivity(intent);
                finish();
            }
        });

        sendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MatchUserProfile.this, giverAgreement.class);
                Bundle b = new Bundle();

                //To-Do add to bundle all loan details---from find match
                b.putString("userName", currentUserName);
                b.putInt("amount",amount);
                b.putString("period", period);
                b.putString("description", description);
                b.putFloat("interest", interest);
                b.putInt("offerLoanId", offerLoanId);
                b.putInt("requestLoanId", requestLoanId);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                startActivity(browserIntent);
            }
        });

        if(facebookUrl!=null) {
            SpannableString content = new SpannableString(facebookUrl);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            facebook.setText(content);
            //facebook.setTextCursorDrawable(Cursor.FIELD_TYPE_BLOB);

        }

    }

    private void loadInfo() {
        userInfo userInfo;
        userInfo= ViewModel.getInstance().loadUserInfo(currentUserName);
        this.university.setText("Institution: "+ userInfo.getStudiesInstitute());
        this.mail.setText("Mail: "+userInfo.getMail());
        this.name.setText("Name: "+userInfo.getFullName());
        this.phone.setText("Phone: "+userInfo.getPhone());
        this.facebook.setText("Facebook Account");
        facebookUrl=userInfo.getFacebook();
    }

    private void loadImg() {
        Query q= mDatabaseRef.orderByChild("name").equalTo(currentUserName);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Upload u= dataSnapshot1.getValue(Upload.class);
                    Picasso.with(MatchUserProfile.this)
                            .load(u.getImageUrl())
                            .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MatchUserProfile.this,"Couldn't load image",Toast.LENGTH_SHORT).show();
            }
        });

    }



}
