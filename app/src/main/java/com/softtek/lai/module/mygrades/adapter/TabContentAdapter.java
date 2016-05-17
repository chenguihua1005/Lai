package com.softtek.lai.module.mygrades.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Adapter;

import java.util.List;

/**
 * Created by jerry.guan on 4/11/2016.
 */
public class TabContentAdapter extends FragmentPagerAdapter{

    String[] titles={"日排名","周排名"};
    List<Fragment> fragments;

    public TabContentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
