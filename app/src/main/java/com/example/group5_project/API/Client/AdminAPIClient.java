package com.example.group5_project.API.Client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminAPIClient {
    private  static String baseURL = "https://6494eaecb08e17c9179179b5.mockapi.io/";

    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
