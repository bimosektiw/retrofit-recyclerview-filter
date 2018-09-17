package com.example.bimosektiw.searchnews.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bimosektiw.searchnews.MvpActivity;
import com.example.bimosektiw.searchnews.R;
import com.example.bimosektiw.searchnews.adapter.ArticleAdapter;
import com.example.bimosektiw.searchnews.model.ArticleListModel;
import com.example.bimosektiw.searchnews.model.ArticleRespModel;
import com.example.bimosektiw.searchnews.presenter.ArticlePresenter;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends MvpActivity implements ArticleAdapter.ArticleAdapterListener{

    private ArticlePresenter articlePresenter;
    private String sourceUrl, sourceTitle;
    private ArticleAdapter adapter;
    private List<ArticleListModel> articleListModel;
    private SearchView searchView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rv_article)
    RecyclerView recyclerView;
    @BindView(R.id.text_datanotfound)
    TextView textDataNotFound;
    @BindView(R.id.text_notconnect)
    TextView textNotConnect;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);

        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            sourceUrl = bundle.getString("url");
            sourceTitle = bundle.getString("title");
        }
        initPresenter();
        setToolbar();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        articlePresenter.getArticles();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onArticleSelected(ArticleListModel articleListModel) {
        goWebView(articleListModel);
    }


    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(sourceTitle);
    }

    private void initPresenter(){
        articlePresenter = new ArticlePresenter(this, sourceUrl);
    }

    public void setArticleList(ArticleRespModel articleRespModel){
        progressBar.setVisibility(View.GONE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        articleListModel = articleRespModel.getArticles();
        adapter = new ArticleAdapter(this, articleListModel, this, format);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ArticleAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                goWebView(articleListModel.get(position));
            }
        });
    }

    private void goWebView(ArticleListModel articleListModel){
        String articleUrl = articleListModel.getUrl();

        Bundle bundle = new Bundle();
        bundle.putString("articleUrl",articleUrl);
        Intent intent = new Intent(this, DetailArticleActivity.class);
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
}
