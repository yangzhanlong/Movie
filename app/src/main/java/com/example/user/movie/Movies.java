package com.example.user.movie;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {
    private String imgurl;
    private String title;
    private String overview;
    private String release_date;
    private int vote_average;

    public Movies(String title, String overview, String release_date, int vote_average, String imgurl) {
        this.imgurl = imgurl;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgurl);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeInt(this.vote_average);
    }

    protected Movies(Parcel in) {
        this.imgurl = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readInt();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
