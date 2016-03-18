package com.softtek.lai.module.File.adapter;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.softtek.lai.R;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.R.id;

/**
 * Created by julie.zhu on 3/18/2016.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    private Activity activity;

    public ViewPagerAdapter(List<View> views, Activity activity) {
        this.views=views;
        this.activity = activity;
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {
            ImageView btn_creat = (ImageView) arg0.findViewById(R.id.btn_creat);
            btn_creat.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   creat();
                }
            });
        }
        return views.get(arg1);
    }

    private void creat() {
        Intent intent = new Intent(activity, CreatFlleActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}

