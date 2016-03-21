package com.softtek.lai.module.grade.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.softtek.lai.module.grade.view.LossWeightFragment;

import java.util.List;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class TabContentAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    public TabContentAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
