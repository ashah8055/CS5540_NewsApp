package com.example.amar.ads_newsapp_2.Utilities;

import android.net.Uri;
import android.util.Log;

import com.example.amar.ads_newsapp_2.Model.NewsItemClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Amar on 6/29/2017.
 */

public class NetworkUtils {
    private static final String BASE_URL = "https://newsapi.org/v1/articles";

    final static String SOURCE_PARAMETER = "source";
    final static String SORT_PARAMETER = "sortBy";
    final static String APIKEY_PARAMETER = "apiKey";

    private final static String src = "the-next-web";
    private final static String sort = "latest";
    private final static String key = "a3b8f59e2bde4d68bafc3c11289296a8";

    //Done TODO 5.Create a static method in NetworkUtils that uses Uri.Builder to build the appropriate url, the url you used in (2), to return a completed Java URL.

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAMETER, src)
                .appendQueryParameter(SORT_PARAMETER, sort)
                .appendQueryParameter(APIKEY_PARAMETER, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    //6. Done TODO Put this method in your NetworkUtils class:
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

  public static ArrayList<NewsItemClass> parseJSON(String json) throws JSONException
    {
        ArrayList<NewsItemClass> newsInfo = new ArrayList<>();
        JSONObject newsJson = new JSONObject(json);
        JSONArray newsArray = newsJson.getJSONArray("articles");
        String nSource = newsJson.getString("source");
        String nAuthor;
        String nTitle;
        String nDescription;
        String nUrl;
        String nUrlToImage;
        String nPublishedAt;

        for(int j = 0; j < newsArray.length(); j++)
        {
            JSONObject newsdata = newsArray.getJSONObject(j);
            nAuthor = newsdata.getString("author");
            nDescription= newsdata.getString("description");
            nTitle = newsdata.getString("title");
            nUrl = newsdata.getString("url");
            nUrlToImage = newsdata.getString("urlToImage");
            nPublishedAt = newsdata.getString("publishedAt");

            NewsItemClass newsItem = new NewsItemClass(nSource, nAuthor, nTitle, nDescription, nUrl, nUrlToImage, nPublishedAt);
            newsInfo.add(newsItem);
        }

        return newsInfo;
    }


}
