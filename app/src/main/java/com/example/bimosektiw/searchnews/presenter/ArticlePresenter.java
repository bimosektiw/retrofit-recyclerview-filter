package com.example.bimosektiw.searchnews.presenter;

import com.example.bimosektiw.searchnews.BaseActivity;
import com.example.bimosektiw.searchnews.model.ArticleRespModel;
import com.example.bimosektiw.searchnews.service.NewsApiBuilder;
import com.example.bimosektiw.searchnews.service.NewsApiService;
import com.example.bimosektiw.searchnews.ui.ArticleActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlePresenter extends AbstractPresenter {

    private ArticleActivity articleActivity;
    private String sourceUrl;

    public ArticlePresenter(ArticleActivity articleActivity, String sourceUrl){
        super(articleActivity);
        this.articleActivity = articleActivity;
        this.sourceUrl = sourceUrl;
    }

    public void getArticles(){
        NewsApiService apiService = new NewsApiBuilder().getService();
        Call<ArticleRespModel> sourceCall = apiService.getArticleList(sourceUrl, BaseActivity.API_KEY);
        sourceCall.enqueue(new Callback<ArticleRespModel>() {
            @Override
            public void onResponse(Call<ArticleRespModel> call, Response<ArticleRespModel> response) {
                if(response.isSuccessful()){
                    ArticleRespModel articleRespModel = response.body();
                    if(articleRespModel.getTotalResults() != 0){
                        articleActivity.setArticleList(articleRespModel);
                    }
                    else {
                        articleActivity.dataNotFound();
                    }
                }
                else{
                    System.out.print("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<ArticleRespModel> call, Throwable t) {
                articleActivity.unSuccessfulRequest();
            }
        });
    }
}
