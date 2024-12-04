package com.example.foodfresh;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientScalar {
    private static final String BASE_URL = "http://43.201.27.229:8080";

    public static RetrofitInterface getApiService(){return getInstance().create(RetrofitInterface.class);}

    // plain text 응답을 처리하기 위한 retrofit Client
    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
