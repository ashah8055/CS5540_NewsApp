package com.example.amar.ads_newsapp_2.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.amar.ads_newsapp_2.Newsdata.DbNewsHelper;
import com.example.amar.ads_newsapp_2.Newsdata.DbNewsUtils;
import com.example.amar.ads_newsapp_2.Newsdata.NewsItemClass;
import com.firebase.jobdispatcher.JobService;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Amar on 7/26/2017.
 */

public class NewsJob extends JobService {
    AsyncTask mBTask;


    //This method will refresh the news data with the use of firebase
    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                Toast.makeText(NewsJob.this, "Reloaded News Data", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                refreshArticles(NewsJob.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                super.onPostExecute(o);

            }
        };

        mBTask.execute();

        return true;
    }
@Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {

        if (mBTask != null) mBTask.cancel(false);

        return true;
    }
// this method will check for new news data when user click on refresh
    public static void refreshArticles(Context context) {
        ArrayList<NewsItemClass> result = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DbNewsHelper(context).getWritableDatabase();

        try {
            DbNewsUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DbNewsUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }

}
