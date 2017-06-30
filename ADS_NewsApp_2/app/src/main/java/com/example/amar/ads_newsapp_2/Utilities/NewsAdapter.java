package com.example.amar.ads_newsapp_2.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amar.ads_newsapp_2.Model.NewsItemClass;
import com.example.amar.ads_newsapp_2.R;

import java.util.ArrayList;

/**
 * Created by Amar on 6/29/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>
{

    private ArrayList<NewsItemClass> data;
    private static final String TAG = NewsAdapter.class.getSimpleName();

    final private ItemClickListener newsListener;

    public NewsAdapter(ArrayList<NewsItemClass> data, ItemClickListener listener) {
        this.data = data;
        this.newsListener = listener;
    }

    public interface ItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_list_data;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachToParent);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView newsTitle;
        public final TextView newsDescription;
        public final TextView newsTime;

        public NewsAdapterViewHolder(View newsView) {
            super(newsView);
            newsTitle = (TextView) newsView.findViewById(R.id.nTitleView);
            newsDescription = (TextView) newsView.findViewById(R.id.nDescriptionView);
            newsTime = (TextView) newsView.findViewById(R.id.nTimeView);
            newsView.setOnClickListener(this);
        }

        public void bind(int pos) {
            NewsItemClass newsItem = data.get(pos);
            newsTitle.setText(newsItem.getnTitle());
            newsDescription.setText(newsItem.getnDescription());
            newsTime.setText(newsItem.getnDescription());
        }

        @Override
        public void onClick(View view) {
            newsListener.onListItemClick(getAdapterPosition());
        }
    }

    public void setData(ArrayList<NewsItemClass> data) {
        this.data = data;
    }


}
