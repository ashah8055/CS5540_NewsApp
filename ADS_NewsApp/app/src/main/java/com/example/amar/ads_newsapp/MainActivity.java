package com.example.amar.ads_newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.amar.ads_newsapp.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mNewsTextView;
    private TextView mUrlNewsView;
    private EditText mSearchBoxNewsText;
    private ProgressBar mLoadingIndicator;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.news, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            mUrlNewsView.setText("");
            loadNewsData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.news_dataView);
        mSearchBoxNewsText = (EditText) findViewById(R.id.news_searchView);
        mUrlNewsView = (TextView) findViewById(R.id.news_urlView);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

         //   loadNewsData();
    }

    private void loadNewsData() {

        URL NewsSearchUrl = NetworkUtils.buildUrl();
        mUrlNewsView.setText(NewsSearchUrl.toString());


        new getNewsData().execute();
    }
// Done 8 TODO Extend and implement a subclass of AsyncTask to handle the http request. Display the results in a textview.
// Done 10 TODO spinning progress bars that turn on when the task is running
    public class getNewsData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            URL newsRequestURL = NetworkUtils.buildUrl();

            try {
                return NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
// Done 10 TODO spinning progress bars that turn off

        @Override
        protected void onPostExecute(String newsData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (newsData != null) {
                mNewsTextView.setText(newsData);
            }
        }
    }
}
