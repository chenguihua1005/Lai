/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jerry.guan on 3/23/2016.
 *
 */
public class FragementAdapter extends FragmentPagerAdapter {

    private String[] titles = {"活动推荐", "产品信息", "品牌动向"};
    private List<Fragment> fragmentList;
    private FragmentManager fm;

    public FragementAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fm=fm;
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
