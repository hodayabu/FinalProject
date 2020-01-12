package com.example.finalproject.UI.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.ServerRequests.ViewModel;
import com.example.finalproject.UI.FirebaseConnection.AddImgToFirebase;

public class MainActivity extends AppCompatActivity {
    Button logInButton;
    EditText logInName;
    EditText password;
    TextView forgotPasswordLable;
    TextView signUp;

    public static final String SHARED_PREFS = "sharedPrefs";

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInButton=(Button)findViewById(R.id.SignIn);
        logInName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        forgotPasswordLable=(TextView) findViewById(R.id.forgotPassword);
        signUp=(TextView) findViewById(R.id.singUp);
        ViewModel.getInstance().setContext(this);


//server get call example- to delete when finish organize code!!
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("http://192.168.1.16:3000/")//the server url
//                .addConverterFactory(GsonConverterFactory.create())//convert json that returns from server to java object that we created
//                .build();
//
//        jsonPlaceHolderApi jsonPlaceHolderApi= retrofit.create(com.example.finalproject.ServerRequests.jsonPlaceHolderApi.class);

//        jsonPlaceHolderApi jsonPlaceHolderApi=ViewModel.getInstance().getJsonPlaceHolderApi();
//        Call<List<Post>> call= jsonPlaceHolderApi.getPost();//create the request
//        call.enqueue(new Callback<List<Post>>() {//insert request to queque
//            @Override
//            public void onResponse(Call<List<Post>> call, retrofit2.Response<List<Post>> response) {
//                if(!response.isSuccessful()){
//                    logInName.setText("CodeError: "+ response.code());
//                    return;
//                }
//                List<Post> posts=response.body();
//                logInName.setText("response   "+posts.get(0).getAns());
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                logInName.setText(t.getMessage());
//            }
//        });



        logInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
            if(singIn(logInName.getText().toString(),password.getText().toString())) {
                logInName.setText("");
                password.setText("");
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
            }
            else {
                alert("One Or More Details Are Incorrect");
            }
            }
        });



        forgotPasswordLable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, AddImgToFirebase.class);
                startActivity(intent);



//                Intent intent = new Intent(MainActivity.this, newLoanRequest.class);
//                startActivity(intent);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);            }
        });

    }
    private boolean singIn(String name,String password) {

        return ViewModel.getInstance().signIn(name,password);

    }

    public static void toast(String input,Context c){
        Toast.makeText(c,input,Toast.LENGTH_SHORT).show();
    }


    /**
    private void jsonParse() {

        String url = "https://192.168.1.16/test";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("ans");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                String firstName = employee.getString("name");
//                                int age = employee.getInt("age");
//                                String mail = employee.getString("mail");

                                logInName.append(firstName + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("tttttttttttttttttttt");
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
 **/



    public void alert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
