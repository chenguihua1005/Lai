package com.softtek.lai.module.bodygame3.head.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.softtek.lai.R;

import java.util.List;

/**
 * Created by shelly.xu on 11/22/2016.
 */

public class PhotoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> photos;
    private Context context;

    public PhotoAdapter(Context context, List<String> photos) {
        this.context = context;
        this.photos = photos;
        LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int postion) {
        return photos.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int postion, View contextview, ViewGroup viewGroup) {

        return contextview;
    }


}
