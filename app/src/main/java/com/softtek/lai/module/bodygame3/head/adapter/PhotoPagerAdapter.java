/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame3.head.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.softtek.lai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by julie.zhu on 3/18/2016.
 */
public class PhotoPagerAdapter extends PagerAdapter {
    private List<View> views;
    private Context mcontext;
    private List<String> photos;

    public PhotoPagerAdapter(List<View> views, Context context, List<String> photos) {
        this.views = views;
        this.photos = photos;
        this.mcontext = context;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i("pos:", views.get(position) + "");
        Log.i("photos", photos.get(position));
        View view = views.get(position);
        ImageView iv_grid = (ImageView) view.findViewById(R.id.iv_grid);
        Picasso.with(mcontext).load(AddressManager.get("photoHost") + photos.get(position)).fit()
                .error(R.drawable.default_icon_square)
                .placeholder(R.drawable.default_icon_square).into(iv_grid);
        container.addView(view);
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
}

