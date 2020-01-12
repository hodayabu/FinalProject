package com.example.finalproject.ServerRequests;

import android.content.Context;

import com.example.finalproject.ResponseObjects.askedLoans;
import com.example.finalproject.ResponseObjects.gaveAndOweLoans;
import com.example.finalproject.ResponseObjects.postOffer;
import com.example.finalproject.ResponseObjects.postRequest;
import com.example.finalproject.ResponseObjects.postedLoan;
import com.example.finalproject.ResponseObjects.userInfo;
import com.example.finalproject.UI.Activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
Singeltone class
 */
public class ViewModel {

    private static ViewModel viewModel;
    private com.example.finalproject.ServerRequests.jsonPlaceHolderApi jsonPlaceHolderApi;
    private static Context context;


    private ViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://132.73.195.25:3000/")//the server url
                .addConverterFactory(GsonConverterFactory.create())//convert json that returns from server to java object that we created
                .build();
        jsonPlaceHolderApi = retrofit.create(com.example.finalproject.ServerRequests.jsonPlaceHolderApi.class);
    }

    public static ViewModel getInstance() {
        if (viewModel == null)
            viewModel = new ViewModel();

        return viewModel;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public jsonPlaceHolderApi getJsonPlaceHolderApi() {
        return jsonPlaceHolderApi;
    }


    public boolean signIn(String userName, String password) {

        class MyRunnable implements Runnable {
            String name;
            String pass;
            boolean ans;
            String token;

            public MyRunnable(String name, String pass) {
                this.name = name;
                this.pass = pass;
            }

            @Override
            public void run() {
                Map<String, String> json = new HashMap<>();
                json.put("userName", name);
                json.put("password", pass);
                Call<ResponseBody> call = jsonPlaceHolderApi.signIn(json);

                try {
                    Response<ResponseBody> response = call.execute();
                    JSONObject data = null;
                    try {
                        if (response.body() == null) {
                            ans = false;
                            System.out.println("No Body for req");
                        } else {
                            data = new JSONObject(response.body().string());
                            this.ans = true;
                            this.token = "" + data.get("token");
                            MainActivity.setDefaults("token", "" + data.get("token"), context);
                            MainActivity.setDefaults("userName", userName,context);
                            MainActivity.setDefaults("rank", ""+data.get("rank"),context);
                            MainActivity.setDefaults("interest", ""+data.get("interest"),context);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }//MyRunnable

        MyRunnable myRunnable = new MyRunnable(userName, password);
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myRunnable.ans;

    }




    public List<postedLoan> getAllPostedLoans() {


        class MyRunnable implements Runnable {
            List<postedLoan> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<postedLoan>> call = jsonPlaceHolderApi.postedLoans(token);

                try {
                    Response<List<postedLoan>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<postedLoan> posts = response.body();
                        ans=posts;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//run
            }//Runnable

        MyRunnable myRunnable = new MyRunnable();
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myRunnable.ans;
        }


    public List<askedLoans> getAllAskedLoans() {

        class MyRunnable implements Runnable {
            List<askedLoans> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<askedLoans>> call = jsonPlaceHolderApi.askedLoan(token);

                try {
                    Response<List<askedLoans>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<askedLoans> askedLoans = response.body();
                        ans=askedLoans;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//run
        }//Runnable

        MyRunnable myRunnable = new MyRunnable();
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myRunnable.ans;
    }

    public void postNewRequest(String desc, int amount) {
        String token = MainActivity.getDefaults("token", context);
        postRequest postRequest=new postRequest(amount,desc,"videoString");
        Call<ResponseBody> call=jsonPlaceHolderApi.postRequest(token,postRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    MainActivity.toast("PostLoan Request Failed",context);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MainActivity.toast("PostLoan Request Failed",context);

            }
        });
    }

    public List<gaveAndOweLoans> getAllLoansIGave() {

        class MyRunnable implements Runnable {
            List<gaveAndOweLoans> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<gaveAndOweLoans>> call = jsonPlaceHolderApi.loansIGave(token);

                try {
                    Response<List<gaveAndOweLoans>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<gaveAndOweLoans> loansIGave = response.body();
                        ans=loansIGave;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//run
        }//Runnable

        MyRunnable myRunnable = new MyRunnable();
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myRunnable.ans;

    }

    public List<gaveAndOweLoans> getAllLoansIOwe() {

        class MyRunnable implements Runnable {
            List<gaveAndOweLoans> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<gaveAndOweLoans>> call = jsonPlaceHolderApi.loansIOwe(token);

                try {
                    Response<List<gaveAndOweLoans>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<gaveAndOweLoans> loansIGave = response.body();
                        ans=loansIGave;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//run
        }//Runnable

        MyRunnable myRunnable = new MyRunnable();
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myRunnable.ans;

    }

    public userInfo loadUserInfo(String currentUserName) {

        class MyRunnable implements Runnable {

            String userName;
            //Map<String,String> ans=new HashMap<>();
            userInfo ans;

            public MyRunnable(String userName) {
                this.userName = userName;
            }

            @Override
            public void run() {
                Map<String, String> json = new HashMap<>();
                json.put("userName", userName);
                Call<userInfo> call = jsonPlaceHolderApi.userInfo(json);

                try {
                    Response<userInfo> response = call.execute();
                    try {
                        if (response.body() == null) {
                            System.out.println("No Body for req");
                        } else {
                            ans=response.body();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }//MyRunnable

        MyRunnable myRunnable = new MyRunnable(currentUserName);
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myRunnable.ans;
    }

    public void postNewOffer(String period, String rankFilter, int amount) {
        String token = MainActivity.getDefaults("token", context);
        postOffer postOffer=new postOffer(amount,period,Float.parseFloat(rankFilter));
        Call<ResponseBody> call=jsonPlaceHolderApi.postOffer(token,postOffer);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    MainActivity.toast("PostLoan Offer Failed",context);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MainActivity.toast("PostLoan Offer Failed",context);

            }
        });
    }

    public List<askedLoans> findMatch(String rankFilter, String amount) {

        class MyRunnable implements Runnable {
            List<askedLoans> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Map<String,Float> json=new HashMap<>();
                json.put("rankFilter",Float.parseFloat(rankFilter));
                json.put("amount",Float.parseFloat(amount));
                Call<List<askedLoans>> call = jsonPlaceHolderApi.findMatch(token,json);

                try {
                    Response<List<askedLoans>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<askedLoans> match = response.body();
                        ans=match;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//run
        }//Runnable

        MyRunnable myRunnable = new MyRunnable();
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myRunnable.ans;
    }
}