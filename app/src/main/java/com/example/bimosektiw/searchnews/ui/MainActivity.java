package com.example.bimosektiw.searchnews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bimosektiw.searchnews.MvpActivity;
import com.example.bimosektiw.searchnews.R;
import com.example.bimosektiw.searchnews.adapter.SourceAdapter;
import com.example.bimosektiw.searchnews.model.SourceListModel;
import com.example.bimosektiw.searchnews.model.SourceRespModel;
import com.example.bimosektiw.searchnews.presenter.SourcesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity {

    private SourcesPresenter sourcesPresenter;
    private SourceAdapter adapter;
    private List<SourceListModel> sourceListModel;
    private String sourceUrl;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rv_source)
    RecyclerView recyclerView;
    @BindView(R.id.text_datanotfound)
    TextView textDataNotFound;
    @BindView(R.id.text_notconnect)
    TextView textNotConnect;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);

        initPresenter();
        setToolbar();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        sourcesPresenter.getSources();

    }

    private void initPresenter(){
        sourcesPresenter = new SourcesPresenter(this);
    }


    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.toolbar_main_title);
    }

    public void setSourceList(SourceRespModel sourceRespModel){
        progressBar.setVisibility(View.GONE);
        sourceListModel = sourceRespModel.getSources();
        adapter = new SourceAdapter(sourceListModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setOnItemClickListener(new SourceAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                goArticle(sourceListModel.get(position));
            }
        });
    }

    private void goArticle(SourceListModel sourceListModel){
        sourceUrl = sourceListModel.getUrl();
        Bundle bundle = new Bundle();
        bundle.putString("url", splitUrl(sourceUrl));
        bundle.putString("title", sourceListModel.getName());
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void dataNotFound(){
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        textDataNotFound.setVisibility(View.VISIBLE);
    }

    public void unSuccessfulRequest(){
        progressBar.setVisibility(View.GONE);
        textNotConnect.setVisibility(View.VISIBLE);
    }

    private String splitUrl(String rawUrl){
        String[] output;
        if(rawUrl.contains("http://www.")){
            output = rawUrl.split("http://www.");
            rawUrl = output[1];
        }
        else if(rawUrl.contains("https://www.")){
            output = rawUrl.split("https://www.");
            rawUrl = output[1];
        }
        else{
            if(rawUrl.contains("https://")){
                output = rawUrl.split("https://");
                rawUrl = output[1];
            }
            else{
                output = rawUrl.split("http://");
                rawUrl = output[1];
            }
        }
        if(rawUrl.contains("/news")   ){
            output = rawUrl.split("/news");
            rawUrl = output[0];
        }else if(rawUrl.contains("/sport")){
            output = rawUrl.split("/sport");
            rawUrl = output[0];
        }else if(rawUrl.contains("/home")){
            output = rawUrl.split("/home");
            rawUrl = output[0];
        }else if(rawUrl.contains("/uk")){
            output = rawUrl.split("/uk");
            rawUrl = output[0];
        }else if(rawUrl.contains("/r/all")){
            output = rawUrl.split("/r/all");
            rawUrl = output[0];
        }else if(rawUrl.contains("/")){
            output = rawUrl.split("/");
            rawUrl = output[0];
        }else{

        }
        return rawUrl;
    }
}
