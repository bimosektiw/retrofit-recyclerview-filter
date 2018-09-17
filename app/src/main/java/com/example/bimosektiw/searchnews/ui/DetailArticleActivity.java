package com.example.bimosektiw.searchnews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bimosektiw.searchnews.MvpActivity;
import com.example.bimosektiw.searchnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailArticleActivity extends MvpActivity {

    private String articleUrl;

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        ButterKnife.bind(this);

        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            articleUrl = bundle.getString("articleUrl");
            webView.loadUrl(articleUrl);
            webView.setWebViewClient(new WebViewClient());
        }
    }
}
