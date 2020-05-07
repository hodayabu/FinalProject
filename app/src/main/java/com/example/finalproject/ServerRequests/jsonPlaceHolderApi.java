package com.example.finalproject.ServerRequests;

import com.example.finalproject.ResponseObjects.agreementData;
import com.example.finalproject.ResponseObjects.askedLoans;
import com.example.finalproject.ResponseObjects.gaveAndOweLoans;
import com.example.finalproject.ResponseObjects.mailMsg;
import com.example.finalproject.ResponseObjects.postOffer;
import com.example.finalproject.ResponseObjects.postRequest;
import com.example.finalproject.ResponseObjects.postedLoan;
import com.example.finalproject.ResponseObjects.reviewData;
import com.example.finalproject.ResponseObjects.userInfo;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface jsonPlaceHolderApi {
    @POST("login")
    Call<ResponseBody> signIn(@Body Map<String,String> map);

    @POST("private/getPostedLoans")
    Call<List<postedLoan>> postedLoans(@Header("x-auth-token") String token);

    @POST("private/getRequestLoans")
    Call<List<askedLoans>> askedLoan(@Header("x-auth-token") String token);


    @POST("private/insertRequestLoan")
    Call<ResponseBody> postRequest(@Header("x-auth-token") String token, @Body postRequest post);

    @POST("private/loansIGaved")
    Call<List<gaveAndOweLoans>> loansIGave(@Header("x-auth-token") String token);

    @POST("private/loansIRecieved")
    Call<List<gaveAndOweLoans>> loansIOwe(@Header("x-auth-token") String token);

    @POST("getUserInformation")
    Call<userInfo> userInfo (@Body Map<String,String> map);

    @POST("private/insertPostedLoan")
    Call<ResponseBody> postOffer(@Header("x-auth-token") String token, @Body postOffer post);

    @POST("private/getAllPotenialLoanRequests")
    Call<List<askedLoans>> findMatch(@Header("x-auth-token") String token,@Body Map<String,Float> json);

    @POST("private/AgreementFromGiver")
    Call<ResponseBody> AgreementFromGiver(@Header("x-auth-token") String token, @Body agreementData agreementData);

    @POST("private/getAllwaitingMsg")
    Call<List<agreementData>> getAllwaitingMsg(@Header("x-auth-token") String token);

    @POST("private/AgreementFromReciever")
    Call<ResponseBody> AgreementFromReciever(@Header("x-auth-token") String token,@Body Map<String,String> json);

    @POST("private/getAllwaitingForPayment")
    Call<List<agreementData>> getAllwaitingForPayment(@Header("x-auth-token") String token);

    @POST("pay")
    Call<ResponseBody> paypalTest(@Body Map<String,Integer> map);

    @POST("payBack")
    Call<ResponseBody> paypalBack(@Body Map<String,Integer> map);

    @POST("private/gatAllCompletedLoans")
    Call<List<mailMsg>> gatAllCompletedLoans(@Header("x-auth-token") String token);

    @POST("private/getMonthlyBalance")
    Call<List<gaveAndOweLoans>> monthDebts(@Header("x-auth-token") String token);

    @POST("private/gatAllPayBackCompletedLoans")
    Call<List<mailMsg>> gatAllPayBackCompletedLoans(@Header("x-auth-token") String token);

    @POST("/checkUserFullName")
    Call<ResponseBody> checkUserFullName(@Body Map<String,String> map);

    @POST("/getAllReviews")
    Call<List<reviewData>> getAllReviews(@Body Map<String,String> map);

    @POST("private/sendReview")
    Call<ResponseBody> sendReview(@Header("x-auth-token") String token,@Body Map<String,String> json);

    @POST("/register")
    Call<ResponseBody> register(@Body Map<String,String> map);

    @POST("private/fillPersonalDetails")
    Call<ResponseBody> fillPersonalDetails(@Header("x-auth-token") String toke);

    @POST("private/createPersonalDetails")
    Call<ResponseBody> personalDetails(@Header("x-auth-token") String token,@Body Map<String,Integer> json);
}
