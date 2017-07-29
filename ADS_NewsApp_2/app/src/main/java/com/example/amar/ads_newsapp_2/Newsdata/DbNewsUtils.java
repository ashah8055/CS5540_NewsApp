package com.example.amar.ads_newsapp_2.Newsdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.amar.ads_newsapp_2.Newsdata.Contract.NewsItemClass.*;


/**
 * Created by Amar on 7/25/2017.
 */

public class DbNewsUtils {
    // This method will get all the news information
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NEWS_PUBLISHED_AT + " DESC"
        );
        return cursor;
    }

    // This method is used to insert the news api
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItemClass> newsItems) {

        db.beginTransaction();
        try {
            for (NewsItemClass a : newsItems) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NEWS_SOURCE, a.getnAuthor());
                cv.put(COLUMN_NEWS_AUTHOR, a.getnAuthor());
                cv.put(COLUMN_NEWS_TITLE, a.getnTitle());
                cv.put(COLUMN_NEWS_DESCRIPTION, a.getnDescription());
                cv.put(COLUMN_NEWS_URL, a.getnUrl());
                cv.put(COLUMN_NEWS_URL_TO_IMAGE, a.getnUrlToImage());
                cv.put(COLUMN_NEWS_PUBLISHED_AT, a.getnPublishedAt());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
}
