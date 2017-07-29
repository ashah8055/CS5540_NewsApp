package com.example.amar.ads_newsapp_2.Newsdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Amar on 7/25/2017.
 */

public class DbNewsHelper extends SQLiteOpenHelper {
    // Create a  variable name Db_ChangesMade that will get incremented on db changes.
    private static final int Db_ChangesMade = 1;

    // Created a variable name LOG for logging and Db_Name
    private static final String LOG = "db_Log";

    private static final String Db_Name = "newsdata.db";

    public DbNewsHelper(Context context) {
        super(context, Db_Name, null, Db_ChangesMade);
    }

    //In the onCreate method Set the table col with data type and constrain
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String NEWSTABLE = "CREATE TABLE " + Contract.NewsItemClass.TABLE_NAME + " (" +
                Contract.NewsItemClass._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.NewsItemClass.COLUMN_NEWS_TITLE + " TEXT NOT NULL, " +
                Contract.NewsItemClass.COLUMN_NEWS_AUTHOR + " TEXT NOT NULL, " +
                Contract.NewsItemClass.COLUMN_NEWS_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.NewsItemClass.COLUMN_NEWS_SOURCE + " TEXT NOT NULL, " +
                Contract.NewsItemClass.COLUMN_NEWS_URL + " TEXT NOT NULL, " +
                Contract.NewsItemClass.COLUMN_NEWS_URL_TO_IMAGE + " TEXT NOT NULL, " +
                Contract.NewsItemClass.COLUMN_NEWS_PUBLISHED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        Log.d(LOG, "SQL: " + NEWSTABLE);
        db.execSQL(NEWSTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If DATABASE_VERSION changes, drop table and create new db.
        db.execSQL("DROP TABLE IF EXISTS " + Contract.NewsItemClass.TABLE_NAME);
        onCreate(db);
    }
}
