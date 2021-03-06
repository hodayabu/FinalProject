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

public class userProfile extends menuActivity {

    TextView name;
    TextView facebook;
    TextView phone;
    TextView mail;
    TextView university;
    ImageView profilePic;
    String currentUserName;
    private DatabaseReference mDatabaseRef;
    String facebookUrl;
    Button reviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name=(TextView)findViewById(R.id.Name);
        facebook=(TextView)findViewById(R.id.facebook);
        phone=(TextView)findViewById(R.id.phone);
        mail=(TextView)findViewById(R.id.mail);
        university=(TextView)findViewById(R.id.university);
        profilePic=(ImageView)findViewById(R.id.profileImg) ;
        reviews=(Button) findViewById(R.id.show_reviews) ;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        Bundle b = getIntent().getExtras();
        if(b != null)
            currentUserName = b.getString("userName");

        loadImg();
        loadInfo();

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                startActivity(browserIntent);
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(userProfile.this, userReviews.class);
                Bundle b = new Bundle();
                b.putString("user",currentUserName );
                startActivity(intent);
                finish();
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
                    Picasso.with(userProfile.this)
                            .load(u.getImageUrl())
                            .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(userProfile.this,"Couldn't load image",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
