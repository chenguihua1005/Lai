/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.act.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jarvis on 3/23/2016.
 *
 */
public class ActFragmentAdapter extends FragmentPagerAdapter {

    private String[] titles = {"简介","战况"};
    private List<Fragment> fragmentList;

    public ActFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentList=fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
