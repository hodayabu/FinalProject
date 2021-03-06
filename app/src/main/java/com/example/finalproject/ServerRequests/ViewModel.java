package com.example.finalproject.ServerRequests;

import android.content.Context;

import com.example.finalproject.ResponseObjects.agreementData;
import com.example.finalproject.ResponseObjects.askedLoans;
import com.example.finalproject.ResponseObjects.gaveAndOweLoans;
import com.example.finalproject.ResponseObjects.mailMsg;
import com.example.finalproject.ResponseObjects.postOffer;
import com.example.finalproject.ResponseObjects.postRequest;
import com.example.finalproject.ResponseObjects.postedLoan;
import com.example.finalproject.ResponseObjects.reviewData;
import com.example.finalproject.ResponseObjects.userInfo;
import com.example.finalproject.UI.Activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
                .baseUrl("http://sbserver-env.eba-ppt5gwe6.us-east-2.elasticbeanstalk.com/")//the server url
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
                            MainActivity.setDefaults("totalLoans", "" + data.get("totalLoan"),context);
                            MainActivity.setDefaults("totalOwes", "" + data.get("totalOwes"),context);

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

    public void postNewRequest(String desc, int amount, float rank) {
        String token = MainActivity.getDefaults("token", context);
        postRequest postRequest=new postRequest(amount,desc,"videoString", rank);
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

    public int postNewOffer(String period, String rankFilter, int amount) {
        //Unsyncronic request
//        String token = MainActivity.getDefaults("token", context);
//        postOffer postOffer=new postOffer(amount,period,Float.parseFloat(rankFilter));
//        Call<ResponseBody> call=jsonPlaceHolderApi.postOffer(token,postOffer);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(!response.isSuccessful()){
//                    MainActivity.toast("PostLoan Offer Failed",context);
//                }
//                //need to return loan id ?
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                MainActivity.toast("PostLoan Offer Failed",context);
//
//            }
//        });











        class MyRunnable implements Runnable {
            String ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                postOffer postOffer=new postOffer(amount,period,Float.parseFloat(rankFilter));
                Call<ResponseBody> call=jsonPlaceHolderApi.postOffer(token,postOffer);

                try {
                    Response<ResponseBody> response = call.execute();
                    JSONObject data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        data = new JSONObject(response.body().string());

                        this.ans = "" + data.get("loanId");

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
        int id=Integer.parseInt(myRunnable.ans);
        return id;

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

    public boolean checkMailBox() {
        //call server- if there are new mails return true
        return true;
    }

    public Map<String,Float> calculateRank(int amount) {
        //return 3;
        class MyRunnable implements Runnable {

            float interest;
            float rank;

            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Map<String, Integer> json = new HashMap<>();
                json.put("amount", amount);
                Call<ResponseBody> call = jsonPlaceHolderApi.calculateRank(token, json);

                try {
                    Response<ResponseBody> response = call.execute();
                    JSONObject data = null;
                    try {
                        if (response.body() == null) {
                            interest = 3;
                            System.out.println("No Body for req");
                        } else {
                            data = new JSONObject(response.body().string());
                            this.interest = Float.parseFloat(""+data.get("interest"));
                            this.rank = Float.parseFloat(""+data.get("userRank"));
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

        MyRunnable myRunnable = new MyRunnable();
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String,Float> ans = new HashMap<>();
        ans.put("interest",myRunnable.interest);
        ans.put("rank",myRunnable.rank);
        return ans;

    }

    public boolean checkUserFullName(String fullName, String userName) {

        class MyRunnable implements Runnable {
            String name;
            String userName;
            boolean ans;

            public MyRunnable(String name, String userName) {
                this.name = name;
                this.userName = userName;
            }

            @Override
            public void run() {
                Map<String, String> json = new HashMap<>();
                json.put("fullName", name);
                json.put("userName", userName);
                Call<ResponseBody> call = jsonPlaceHolderApi.checkUserFullName(json);

                try {
                    Response<ResponseBody> response = call.execute();
                    JSONObject data = null;
                    try {
                        if (response.body() == null) {
                            ans = false;
                            System.out.println("No Body for req");
                        } else {
                            data = new JSONObject(response.body().string());
                            if(data.get("value").equals("true"))
                                this.ans = true;
                            else this.ans=false;
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

        MyRunnable myRunnable = new MyRunnable(fullName, userName);
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myRunnable.ans;




    }


    //////////////////////////////////////TO DO on 02/04/2020//////////////////////////

    public void AgreementFromGiver(String giver, String receiver, String period, String description, int amount, float interest, int offerId,int requestId) {
    //void-->syncronic request from server
    //convert the period to date
        Date c = Calendar.getInstance().getTime();
        Date added=addMonth(c,Integer.parseInt(period));
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(added);

        String token = MainActivity.getDefaults("token", context);
        agreementData agreementData=new agreementData(0,giver,receiver,formattedDate,description,amount,interest,offerId,requestId,false);
        Call<ResponseBody> call=jsonPlaceHolderApi.AgreementFromGiver(token,agreementData);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    MainActivity.toast("Agreement For Loan Failed",context);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MainActivity.toast("Agreement For Loan Failed",context);

            }
        });
    }


    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }


    public List<agreementData> getAllwaitingMsg() {

        class MyRunnable implements Runnable {
            List<agreementData> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<agreementData>> call = jsonPlaceHolderApi.getAllwaitingMsg(token);

                try {
                    Response<List<agreementData>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<agreementData> allmsg = response.body();
                        ans=allmsg;

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

    public void AgreementFromReciever(int loanId, String giver) {
        //void-->syncronic request from server
        //convert the period to date

        String token = MainActivity.getDefaults("token", context);
        Map<String,String> json=new HashMap<>();
        json.put("giver",giver);
        json.put("loanId",loanId+"");
        Call<ResponseBody> call=jsonPlaceHolderApi.AgreementFromReciever(token,json);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    MainActivity.toast("Agreement For Loan Failed",context);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MainActivity.toast("Agreement For Loan Failed",context);

            }
        });





        //change field in loanId record at agreement progress to boolean 1
        //add to "mail" table under category 2 with the giver user name
        //remove request from requested_loans in server(where the requestId is the requestId on table approval_progress
    }

    public List<agreementData> getAllwaitingForPayment() {

        class MyRunnable implements Runnable {
            List<agreementData> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<agreementData>> call = jsonPlaceHolderApi.getAllwaitingForPayment(token);

                try {
                    Response<List<agreementData>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<agreementData> allmsg = response.body();
                        ans=allmsg;

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

    public String paypalTest(int amount,int loanId) {

        class MyRunnable implements Runnable {

            String ans;


            @Override
            public void run() {

                Map<String, Integer> json = new HashMap<>();
                json.put("amount", amount);
                json.put("loanId", loanId);
                Call<ResponseBody> call = jsonPlaceHolderApi.paypalTest(json);

                try {
                    Response<ResponseBody> response = call.execute();
                    JSONObject data = null;
                    try {
                        if (response.body() == null) {
                            ans = "";
                            System.out.println("No Body for req");
                        } else {
                            data = new JSONObject(response.body().string());
                            this.ans = ""+data.get("paymentUrl");
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

    public List<mailMsg> gatAllCompletedLoans() {

        class MyRunnable implements Runnable {
            List<mailMsg> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<mailMsg>> call = jsonPlaceHolderApi.gatAllCompletedLoans(token);

                try {
                    Response<List<mailMsg>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<mailMsg> mailMsg = response.body();
                        ans=mailMsg;

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

    public List<gaveAndOweLoans> getAllMonthDebts() {

        class MyRunnable implements Runnable {
            List<gaveAndOweLoans> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<gaveAndOweLoans>> call = jsonPlaceHolderApi.monthDebts(token);

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
                        System.out.println("))))))))))))))))))))");
                        System.out.println(loansIGave.get(0).getLoanID());

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

    public String paypalBack(int amount, int loanId) {

        class MyRunnable implements Runnable {

            String ans;


            @Override
            public void run() {

                Map<String, Integer> json = new HashMap<>();
                json.put("amount", amount);
                json.put("loanId", loanId);
                Call<ResponseBody> call = jsonPlaceHolderApi.paypalBack(json);

                try {
                    Response<ResponseBody> response = call.execute();
                    JSONObject data = null;
                    try {
                        if (response.body() == null) {
                            ans = "";
                            System.out.println("No Body for req");
                        } else {
                            data = new JSONObject(response.body().string());
                            this.ans = ""+data.get("paymentUrl");



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

    public List<mailMsg> gatAllPayBackCompletedLoans() {


        class MyRunnable implements Runnable {
            List<mailMsg> ans;
            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<List<mailMsg>> call = jsonPlaceHolderApi.gatAllPayBackCompletedLoans(token);

                try {
                    Response<List<mailMsg>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<mailMsg> mailMsg = response.body();
                        ans=mailMsg;

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

    public List<reviewData> getAllReviews(String user) {

        class MyRunnable implements Runnable {
            List<reviewData> ans;
            @Override
            public void run() {
                Map<String, String> json = new HashMap<>();
                json.put("name", user);
                Call<List<reviewData>> call = jsonPlaceHolderApi.getAllReviews(json);

                try {
                    Response<List<reviewData>> response = call.execute();
                    JSONArray data = null;

                    if (response.body() == null) {
                        System.out.println("No Body for req");
                    } else {

                        if (!response.isSuccessful()) {
                            System.out.println(("CodeError: " + response.code()));
                            return;
                        }
                        List<reviewData> allreviews = response.body();
                        ans=allreviews;

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

    public void sendReview(String userName, String review, float rateValue) {


        String token = MainActivity.getDefaults("token", context);
        Map<String,String> json=new HashMap<>();
        json.put("userName",userName);
        json.put("review",review);
        json.put("score", rateValue+"");
        Call<ResponseBody> call=jsonPlaceHolderApi.sendReview(token, json);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    MainActivity.toast("Review send failed",context);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MainActivity.toast("Review send failed",context);

            }
        });


    }

    public boolean register(String username, String password, String fullName, String email, String phone, String grade, String school, String facebook) {
        class MyRunnable implements Runnable {
            boolean ans;


            @Override
            public void run() {
                Map<String, String> json = new HashMap<>();
                json.put("userName", username);
                json.put("password", password);
                json.put("fullName", fullName);
                json.put("email", email);
                json.put("phone", phone);
                json.put("grade", grade);
                json.put("school", school);
                json.put("facebook", facebook);

                Call<ResponseBody> call = jsonPlaceHolderApi.register(json);

                try {
                    Response<ResponseBody> response = call.execute();
                    JSONObject data = null;
                    try {
                        if (response.body() == null) {
                            ans = false;
                            System.out.println("No Body for req");
                        } else {
                            data = new JSONObject(response.body().string());
                            if(data.get("value").equals("true"))
                                this.ans = true;
                            else this.ans=false;
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

    public boolean fillPersonalDetails() {
        class MyRunnable implements Runnable {
            boolean ans;

            @Override
            public void run() {
                String token = MainActivity.getDefaults("token", context);
                Call<ResponseBody> call = jsonPlaceHolderApi.fillPersonalDetails(token);

                try {
                    Response<ResponseBody> response = call.execute();
                    JSONObject data = null;
                    try {
                        if (response.body() == null) {
                            ans = false;
                            System.out.println("No Body for req");
                        } else {
                            data = new JSONObject(response.body().string());
                            if(data.get("value").equals("true"))
                                this.ans = true;
                            else this.ans=false;
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


    public void personalDetails(int income, int age, int parents, int minus, int debts, int facebook, int friends, int instagram) {

        String token = MainActivity.getDefaults("token", context);
        Map<String,Integer> json=new HashMap<>();
        json.put("income",income);
        json.put("age",age);
        json.put("parents", parents);
        json.put("minus", minus);
        json.put("debts", debts);
        json.put("facebook", facebook);
        json.put("friends", friends);
        json.put("instagram", instagram);
        Call<ResponseBody> call=jsonPlaceHolderApi.personalDetails(token, json);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    MainActivity.toast("personalDetails send failed",context);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MainActivity.toast("personalDetails send failed",context);

            }
        });



    }
}