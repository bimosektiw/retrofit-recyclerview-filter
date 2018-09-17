package com.example.bimosektiw.searchnews.service;

import com.example.bimosektiw.searchnews.model.ArticleRespModel;
import com.example.bimosektiw.searchnews.model.SourceRespModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    @GET("/v2/sources")
    Call<SourceRespModel> getSourceList(@Query("language") String language, @Query("apiKey") String apiKey);

    @GET("/v2/everything")
    Call<ArticleRespModel> getArticleList(@Query("domains") String domains, @Query("apiKey") String apiKey);
}
