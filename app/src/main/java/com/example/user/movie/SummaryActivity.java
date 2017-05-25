package com.example.user.movie;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class SummaryActivity extends AppCompatActivity {

    @BindViews ({R.id.tv_title, R.id.overview, R.id.date, R.id.vote_average})
    public List<TextView> textViewList;
    private SimpleDraweeView draweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Movies movies = getIntent().getParcelableExtra("movies");

        setTitle(getString(R.string.movie_detail));
        String imgurl = movies.getImgurl();
        String title = movies.getTitle();
        String overview = movies.getOverview();
        String release_date = movies.getRelease_date();
        int vote_average = movies.getVote_average();

        ButterKnife.bind(this);
        textViewList.get(0).setText(title);
        textViewList.get(1).setText(overview);
        textViewList.get(2).setText(release_date);
        textViewList.get(3).setText(Float.toString(vote_average)+"/10");

        Uri uri = Uri.parse(imgurl);
        draweeView = (SimpleDraweeView) findViewById(R.id.im);
        draweeView.setImageURI(uri);
    }
}
