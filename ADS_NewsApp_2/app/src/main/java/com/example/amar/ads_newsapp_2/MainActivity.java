package com.example.amar.ads_newsapp_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.net.Uri;
import android.widget.ProgressBar;

import com.example.amar.ads_newsapp_2.Utilities.NewsAdapter;
import com.example.amar.ads_newsapp_2.Utilities.NewsJob;
import com.example.amar.ads_newsapp_2.Utilities.NewsScheduleUtils;
import com.example.amar.ads_newsapp_2.Newsdata.Contract;
import com.example.amar.ads_newsapp_2.Newsdata.DbNewsHelper;
import com.example.amar.ads_newsapp_2.Newsdata.DbNewsUtils;
// Remove class FetchNewsTask
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener{

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    static final String TAG = "mainactivity";
    private ProgressBar mLoadingIndicator;

    //Created SQLiteDatabase object with name mNewsData and Cursor with name nNewscursor
    private SQLiteDatabase mNewsData;
    private Cursor nNewscursor;

    // Created  mNews_DataLoder int to uniquely identify loader
    private static final int mNews_DataLoder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gets the id of recycler view for display.
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // It checks weather news app has been for first time or not
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean First = prefs.getBoolean("First", true);

        if (First) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.restartLoader(mNews_DataLoder, null, this).forceLoad();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("First", false);
            editor.commit();
        }
        NewsScheduleUtils.scheduleRefresh(this);
    }


    // Created methods onCreateLoader, onLoadFinished, and onLoaderReset for LoaderManager
    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {


        return new AsyncTaskLoader<Void>(this) {

            // onStartLoading method is to start loading news by setting mLoadingIndicator.setVisibility(View.VISIBLE);
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }
            // loadInBackground method will refresh articles
            @Override
            public Void loadInBackground() {

                NewsJob.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        //Get a writable database reference and store in mDb
        mNewsData = new DbNewsHelper(MainActivity.this).getReadableDatabase();
        nNewscursor = DbNewsUtils.getAll(mNewsData);
        mNewsAdapter = new NewsAdapter(nNewscursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int itemNumber = item.getItemId();

        switch (itemNumber) {
            case R.id.action_search:
                LoaderManager loaderManager = getSupportLoaderManager();
                loaderManager.restartLoader(mNews_DataLoder, null, this).forceLoad();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // When loading is finished, hide the loading indicator.
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mNewsData = new DbNewsHelper(MainActivity.this).getReadableDatabase();
        nNewscursor = DbNewsUtils.getAll(mNewsData);
        mNewsAdapter = new NewsAdapter(nNewscursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {}

    // Update onListItemClick method to get the cursor
    @Override
    public void onListItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.NewsItemClass.COLUMN_NEWS_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
