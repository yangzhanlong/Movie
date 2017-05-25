package com.example.user.movie;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movies>> {
    private ArrayList<Movies> mList;
    private Context mContext;
    private GridView mGridView;

    public FetchMovieTask(Context context, ArrayList<Movies> list, GridView gridView) {
        mContext = context;
        mList = list;
        mGridView = gridView;
    }

    @Override
    protected ArrayList<Movies> doInBackground(String... params) {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(mContext);
        String orderType = sharedPrefs.getString(
                mContext.getString(R.string.pref_order_key),
                mContext.getString(R.string.pref_order_popular));


        String orderParam;
        if (orderType.equals(mContext.getString(R.string.pref_order_top_rated))) {
            orderParam = mContext.getString(R.string.pref_order_top_rated);
        } else {
            orderParam = mContext.getString(R.string.pref_order_popular);
        }

        final String FORECAST_BASE_URL =
                "http://api.themoviedb.org/3/movie/" + orderParam + "?";
        final String LANGUAGE_PARAM = "language";
        final String APIKEY_PARAM = "api_key";

        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(LANGUAGE_PARAM, "zh")
                .appendQueryParameter(APIKEY_PARAM, BuildConfig.OPEN_MOVIE_API_KEY)
                .build();

        Log.v("Fetach", builtUri.toString());
        HttpURLConnection conn = null;
        try {
            URL url = new URL(builtUri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream inputStream = conn.getInputStream();
                String result = GetStringFromInputStream.getString(inputStream);
                JSONObject object = new JSONObject(result);
                JSONArray array = object.getJSONArray("results");

                mList.clear();
                for (int i = 0; i < array.length(); i++) {
                    String poster_path = array.getJSONObject(i).getString("poster_path");
                    String title = array.getJSONObject(i).getString("title");
                    String overview = array.getJSONObject(i).getString("overview");
                    String release_date = array.getJSONObject(i).getString("release_date");
                    int vote_average = array.getJSONObject(i).getInt("vote_average");

                    String path = "http://image.tmdb.org/t/p/w185" + poster_path;
                    mList.add(new Movies(title, overview, release_date, vote_average, path));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mList;
    }

    @Override
    protected void onPostExecute(ArrayList<Movies> list) {
        super.onPostExecute(list);
        ImageAdapter adapter = new ImageAdapter(mContext, list);
        mGridView.setAdapter(adapter);
    }

}
