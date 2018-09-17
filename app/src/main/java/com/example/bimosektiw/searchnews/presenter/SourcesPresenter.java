package com.example.bimosektiw.searchnews.presenter;

import com.example.bimosektiw.searchnews.BaseActivity;
import com.example.bimosektiw.searchnews.model.SourceRespModel;
import com.example.bimosektiw.searchnews.service.NewsApiBuilder;
import com.example.bimosektiw.searchnews.service.NewsApiService;
import com.example.bimosektiw.searchnews.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourcesPresenter extends AbstractPresenter{

    private MainActivity mainActivity;

    public SourcesPresenter(MainActivity mainActivity){
        super(mainActivity);
        this.mainActivity = mainActivity;
    }

    public void getSources(){
        NewsApiService apiService = new NewsApiBuilder().getService();
        Call<SourceRespModel> sourceCall = apiService.getSourceList("en", BaseActivity.API_KEY);
        sourceCall.enqueue(new Callback<SourceRespModel>() {
            @Override
            public void onResponse(Call<SourceRespModel> call, Response<SourceRespModel> response) {
                if(response.isSuccessful()){
                    SourceRespModel sourceRespModel = response.body();
                    if(sourceRespModel.getStatus().equals("ok")){
                        mainActivity.setSourceList(sourceRespModel);
                    }
                    else{
                        mainActivity.dataNotFound();
                    }
                }
                else{
                    System.out.print("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<SourceRespModel> call, Throwable t) {
                mainActivity.unSuccessfulRequest();
            }
        });
    }
}
