package com.softtek.lai.module.bodygame3.head.adapter;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.module.bodygame3.activity.adapter.PagerAdapter;

import java.util.List;

/**
 * Created by shelly.xu on 12/27/2016.
 */

public class ImageAdapter extends PagerAdapter {

    public ImageAdapter(List<View> views, Activity activity) {
        super(views, activity);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return super.isViewFromObject(arg0, arg1);
    }
}
