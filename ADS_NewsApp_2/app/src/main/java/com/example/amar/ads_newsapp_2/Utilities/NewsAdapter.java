package com.example.amar.ads_newsapp_2.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amar.ads_newsapp_2.Newsdata.Contract;
import com.example.amar.ads_newsapp_2.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Amar on 6/29/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>
{

    private static final String TAG = NewsAdapter.class.getSimpleName();

    final private ItemClickListener listener;
    private Context context;

    // Added Cursor with name mCursor
    private Cursor mCursor;

    // Updated the constructor to accept cursor and Deleted ArrayList of NewsItemClass data.
    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.mCursor = cursor;
        this.listener = listener;
    }

    // Updated the method to accept Cursor
    public interface ItemClickListener {
        void onListItemClick(Cursor cursor, int clickedItemIndex);
    }

    //Method to set the view of news_list_data
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
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

    // Update the getItemCount to return the getCount of mCursor
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mNewsTitle;
        public final TextView mNewsDescription;
        public final TextView mNewsTime;

        //Add image to recyclerview
        public final ImageView img;

        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsTitle = (TextView) itemView.findViewById(R.id.nTitleView);
            mNewsDescription = (TextView) itemView.findViewById(R.id.nDescriptionView);
            mNewsTime = (TextView) itemView.findViewById(R.id.nTimeView);
            img = (ImageView)itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        public void bind(int pos) {
            mCursor.moveToPosition(pos);

            mNewsTitle.setText(mCursor.getString(mCursor.getColumnIndex(Contract.NewsItemClass.COLUMN_NEWS_TITLE)));
            mNewsDescription.setText(mCursor.getString(mCursor.getColumnIndex(Contract.NewsItemClass.COLUMN_NEWS_DESCRIPTION)));
            mNewsTime.setText(mCursor.getString(mCursor.getColumnIndex(Contract.NewsItemClass.COLUMN_NEWS_PUBLISHED_AT)));

            // Used Picasso to get image for each news item in the recycler view
            String urlToImage = mCursor.getString(mCursor.getColumnIndex(Contract.NewsItemClass.COLUMN_NEWS_URL_TO_IMAGE));
            Log.d(TAG, urlToImage);
            if(urlToImage != null){
                Picasso.with(context)
                        .load(urlToImage)
                        .into(img);
            }
        }

        //Added cursor to make call to listener
        @Override
        public void onClick(View v) {
            listener.onListItemClick(mCursor, getAdapterPosition());
        }
    }

}
