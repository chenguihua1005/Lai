/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class TabContentAdapter extends FragmentPagerAdapter {

    private String[] titles = {"减重斤数", "腰围变化", "体脂率", "减重百分比"};
    private List<Fragment> fragments;

    public TabContentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }*/
}
