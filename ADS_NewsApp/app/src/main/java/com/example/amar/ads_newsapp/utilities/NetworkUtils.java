package com.example.amar.ads_newsapp.utilities;

        import android.net.Uri;
        import android.util.Log;

        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.Scanner;

        import static android.content.ContentValues.TAG;


/**
 * Created by Amar on 6/21/2017.
 */

/*
 Done TODO 1.Visit this site and look around a bit: https://newsapi.org/. Sign up and get your free api key.
*/
//Done TODO 2.Past this url: https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=   with your api key added to the end in a browser.
//Done TODO 3.  In Android Studio, create a new project called "News App" with a blank activity.
//Done TODO 4. Create a new class called NetworkUtils. Define the appropriate base_url and query_parameter constants (make sure they are Java constants) here as static class members.

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


}
