package edu.wgu.student.tomasgray.capstone.data.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface LoginWebService
{
    @GET("login")
    Call<Authorization> login(@Header("Authorization") String authKey);
}
