package com.example.bimosektiw.searchnews.presenter;

import com.example.bimosektiw.searchnews.MvpActivity;

public class AbstractPresenter {
    private MvpActivity mvpActivity;

    public AbstractPresenter(MvpActivity activity){
        this.mvpActivity = activity;
    }

    public MvpActivity getMvpActivity() {
        return mvpActivity;
    }
}
