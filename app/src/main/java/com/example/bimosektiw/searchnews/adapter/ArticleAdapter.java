package com.example.bimosektiw.searchnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bimosektiw.searchnews.R;
import com.example.bimosektiw.searchnews.model.ArticleListModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> implements Filterable{

    private List<ArticleListModel> articleList;
    private List<ArticleListModel> articleListFiltered;
    private ArticleAdapterListener listener;
    private Context context;
    private static MyClickListener myClickListener;
    private SimpleDateFormat dateFormat;


    public ArticleAdapter(Context context, List<ArticleListModel> articleList, ArticleAdapterListener listener, SimpleDateFormat dateFormat){
        this.articleList = articleList;
        this.context = context;
        this.listener = listener;
        this.articleListFiltered = articleList;
        this.dateFormat = dateFormat;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    articleListFiltered = articleList;
                } else {
                    List<ArticleListModel> filteredList = new ArrayList<>();
                    for (ArticleListModel row : articleList) {

                        // title match condition. this might differ depending on your requirement
                        // here we are looking for title match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    articleListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = articleListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                articleListFiltered = (ArrayList<ArticleListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ArticleAdapterListener{
        void onArticleSelected(ArticleListModel articleListModel);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_article_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ArticleListModel article = articleListFiltered.get(position);

        try {
            Date past = dateFormat.parse(article.getPublishedAt());
            Date now = new Date();
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            holder.textTime.setText(String.valueOf(hours)+"h ago");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textTitle.setText(article.getTitle());
        holder.textAuthor.setText(article.getAuthor());

        Context context = holder.imageArticle.getContext();
        Glide.with(context).load(article.getUrlToImage()).into(holder.imageArticle);

    }

    @Override
    public int getItemCount() {
        return articleListFiltered.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textAuthor;
        TextView textTime;
        TextView textTitle;
        ImageView imageArticle;

        public MyViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView)itemView.findViewById(R.id.text_article_title);
            imageArticle = (ImageView)itemView.findViewById(R.id.image_article);
            textAuthor = (TextView)itemView.findViewById(R.id.text_article_author);
            textTime = (TextView)itemView.findViewById(R.id.text_article_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onArticleSelected(articleListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
}
