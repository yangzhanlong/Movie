package com.example.user.movie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class SummaryActivity extends AppCompatActivity {

    private TextView tv_title, tv_overview, tv_release_date, tv_vote_average;
    private SimpleDraweeView draweeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        setTitle(getString(R.string.movie_detail));
        Intent intent = getIntent();
        String imgurl = intent.getStringExtra("imgurl");
        String title = intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        String release_date = intent.getStringExtra("release_date");
        int vote_average = intent.getIntExtra("vote_average", 0);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);

        tv_overview = (TextView) findViewById(R.id.overview);
        tv_overview.setText(overview);

        tv_release_date = (TextView) findViewById(R.id.date);
        tv_release_date.setText(release_date);

        tv_vote_average = (TextView) findViewById(R.id.vote_average);
        tv_vote_average.setText(Float.toString(vote_average)+"/10");

        Uri uri = Uri.parse(imgurl);
        draweeView = (SimpleDraweeView) findViewById(R.id.im);
        draweeView.setImageURI(uri);
    }
}
