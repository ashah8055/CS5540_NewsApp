package com.example.amar.ads_newsapp_2.Newsdata;

import android.provider.BaseColumns;

/**
 * Created by Amar on 7/25/2017.
 */

public class Contract {
    public static final class NewsItemClass implements BaseColumns {

        //Created a different col. members that require to store in table in the DB
        public static final String TABLE_NAME = "news_data";
        public static final String COLUMN_NEWS_SOURCE = "source";
        public static final String COLUMN_NEWS_AUTHOR = "author";
        public static final String COLUMN_NEWS_TITLE = "title";
        public static final String COLUMN_NEWS_DESCRIPTION = "description";
        public static final String COLUMN_NEWS_URL = "url";
        public static final String COLUMN_NEWS_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_NEWS_PUBLISHED_AT = "published_at";
    }

}
