package com.example.amar.ads_newsapp_2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.amar.ads_newsapp_2.Model.NewsItemClass;
import com.example.amar.ads_newsapp_2.Utilities.NetworkUtils;
import com.example.amar.ads_newsapp_2.Utilities.NewsAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private EditText mSearchBox;
    private ProgressBar mLoadingIndicator;
    static final String TAG = "mainactivity";
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mSearchBox = (EditText) findViewById(R.id.news_searchView);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadNewsData();
    }

    private void loadNewsData() {
        new getNewsData("").execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        switch (itemNumber) {
            case R.id.action_search:
                String news_search = mSearchBox.getText().toString();
                getNewsData task = new getNewsData(news_search);
                task.execute();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class getNewsData extends AsyncTask<URL, Void, ArrayList<NewsItemClass>> implements NewsAdapter.ItemClickListener {
        String query;
        ArrayList<NewsItemClass> data;

        getNewsData(String search)
        {
            query = search;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItemClass> doInBackground(URL... params) {
            ArrayList<NewsItemClass> nResult = null;
            URL newsRequestURL = NetworkUtils.buildUrl();

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
                nResult = NetworkUtils.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return nResult;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItemClass> newsData) {
            this.data = newsData;
            super.onPostExecute(data);
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (data != null) {
                NewsAdapter adapterData = new NewsAdapter(data, this);
                mRecyclerView.setAdapter(adapterData);
            }
        }

        @Override
        public void onListItemClick(int clickedItemIndex) {
            openWebPage(data.get(clickedItemIndex).getnUrl());
        }

        public void openWebPage(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
