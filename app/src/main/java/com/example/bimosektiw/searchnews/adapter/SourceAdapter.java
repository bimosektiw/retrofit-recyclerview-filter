package com.example.bimosektiw.searchnews.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bimosektiw.searchnews.R;
import com.example.bimosektiw.searchnews.model.SourceListModel;

import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.MyViewHolder>{

    private static int selectedPos = 0;
    private List<SourceListModel> userList;
    private static MyClickListener myClickListener;

    public SourceAdapter(List<SourceListModel> userList){
        this.userList = userList;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_sources_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SourceListModel user = userList.get(position);

        holder.name.setText(user.getName());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardView;
        TextView name;
        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.text_source_name);
            cardView = (CardView) view.findViewById(R.id.cv_source);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
            selectedPos = getPosition();
            v.setSelected(true);
        }

    }
}