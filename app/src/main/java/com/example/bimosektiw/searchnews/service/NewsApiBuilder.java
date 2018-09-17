package com.example.bimosektiw.searchnews.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiBuilder {
    public static final String BASE_URL = "https://newsapi.org";

    private Retrofit retrofit;

    public NewsApiBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public NewsApiService getService() {
        return retrofit.create(NewsApiService.class);
    }
}
