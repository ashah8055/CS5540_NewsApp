package com.example.amar.ads_newsapp_2.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by Amar on 7/26/2017.
 */

//This class is gets sync with news data at every 50 min interval and gets updated news
public class NewsScheduleUtils {
    private static final int NEWS_INTERVAL_MIN = 50;
    private static final int SYNC_CHNAGE_SEC = 0;
    private static final String NEWS_TOKEN = "news_token";

    private static boolean sInitialized;

    synchronized public static void scheduleRefresh(@NonNull final Context context){
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag(NEWS_TOKEN)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(NEWS_INTERVAL_MIN,
                        NEWS_INTERVAL_MIN + SYNC_CHNAGE_SEC))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;
    }
}
