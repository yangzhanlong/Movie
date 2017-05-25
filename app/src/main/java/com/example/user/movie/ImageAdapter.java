package com.example.user.movie;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Movies> mList;

    public ImageAdapter(Context c, ArrayList<Movies> list) {
        mContext = c;
        mList = list;
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movies movies = (Movies) getItem(position);
        String imgurl = movies.getImgurl();
        Uri uri = Uri.parse(imgurl);
        viewHolder.draweeView.setImageURI(uri);

        return convertView;
    }

    static class ViewHolder{
        SimpleDraweeView draweeView;
    }
}
