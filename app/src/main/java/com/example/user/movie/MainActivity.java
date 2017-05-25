package com.example.user.movie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private View emptyView;
    private ArrayList<Movies> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String imgurl = list.get(position).getImgurl();
                String title = list.get(position).getTitle();
                String overview = list.get(position).getOverview();
                String release_date = list.get(position).getRelease_date();
                int vote_average = list.get(position).getVote_average();
                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                Movies movies = new Movies(title, overview, release_date, vote_average, imgurl);
                Bundle bundle = new Bundle();
                bundle.putParcelable("movies", movies);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        emptyView = findViewById(R.id.empty_view);
        gridView.setEmptyView(emptyView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String orderType = sharedPrefs.getString(
                getString(R.string.pref_order_key),
                getString(R.string.pref_order_popular));

        if (orderType.equals(getString(R.string.pref_order_top_rated))) {
            setTitle(getString(R.string.movie_top_rated));
        } else {
            setTitle(getString(R.string.movie_popular));
        }

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        String type = sharedPreferences.getString("order", orderType);
        if (!orderType.equals(type) || list.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("order", orderType);
            editor.commit();
            updateMovies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void updateMovies() {
        FetchMovieTask fetchMovieTask = new FetchMovieTask(this, list, gridView);
        fetchMovieTask.execute();
    }
}
