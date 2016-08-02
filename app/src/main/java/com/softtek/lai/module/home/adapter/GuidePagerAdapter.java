/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.softtek.lai.R;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.welcome.WelcomeActivity;

import java.util.List;

/**
 * Created by julie.zhu on 3/18/2016.
 */
public class GuidePagerAdapter extends android.support.v4.view.PagerAdapter {
    private List<View> views;
    private Activity activity;

    public GuidePagerAdapter(List<View> views, Activity activity) {
        this.views = views;
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
            Button start_btn = (Button) arg0.findViewById(R.id.start_btn);
            start_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    creat();
                }
            });
        }
        if (arg1 == views.size() - 2) {
            Button skip_btn2 = (Button) arg0.findViewById(R.id.skip_btn2);
            skip_btn2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    creat();
                }
            });
        }
        if (arg1 == views.size() - 3) {
            Button skip_btn = (Button) arg0.findViewById(R.id.skip_btn);
            skip_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    creat();
                }
            });
        }
        return views.get(arg1);
    }

    /*
    * 开启主页
    * */
    private void creat() {
//        Intent intent=new Intent(activity, HomeActviity.class);
//        activity.startActivity(intent);

//        Intent intent=new Intent();
//        activity.setResult(activity.RESULT_OK,intent);
//        activity.finish();
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

