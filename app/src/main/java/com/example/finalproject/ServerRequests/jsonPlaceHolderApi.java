package com.example.finalproject.ServerRequests;

import com.example.finalproject.ResponseObjects.askedLoans;
import com.example.finalproject.ResponseObjects.gaveAndOweLoans;
import com.example.finalproject.ResponseObjects.postOffer;
import com.example.finalproject.ResponseObjects.postRequest;
import com.example.finalproject.ResponseObjects.postedLoan;
import com.example.finalproject.ResponseObjects.userInfo;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface jsonPlaceHolderApi {
    //all of the request to the server with the method (get/post), specific url("aa") and the type of the returned value (Post)
//
//    //get request test
//    @GET("test")
//    Call<List<Post>> getPost();// a call for getPost will define a request to the server with "aa" url finish (after the baseUrl)
//
//    //post request test
//    @POST("postTest")
//    Call<Post> createPost(@Body Post post);
//
//    //post request test with token
//    @POST("tokenTest")
//    Call<Post> token(@Header("x-auth-token") String token);
//
//    //send and recived simple json
//    @POST("jsonTest")
//    Call<ResponseBody> getJson(@Body Map<String,String> s);



    ////////////////////////////// Real Functions!///////////////////////////////
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

}
