package com.example.finalproject.UI.Activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

public class menuActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(menuActivity.this, HomePage.class);
                startActivity(intent);
                return true;
            case R.id.postNewLoanRequest:
                Intent intent1 = new Intent(menuActivity.this, newLoanRequest.class);
                startActivity(intent1);
                return true;
            case R.id.postNewLoanOffer:
                Intent intent2 = new Intent(menuActivity.this, newLoanOffer.class);
                startActivity(intent2);
                return true;
            case R.id.signOut:
                Intent intent3 = new Intent(menuActivity.this, MainActivity.class);
                MainActivity.setDefaults("token", "", menuActivity.this);
                startActivity(intent3);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
