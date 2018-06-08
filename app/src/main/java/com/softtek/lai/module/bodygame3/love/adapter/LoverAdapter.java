package com.softtek.lai.module.bodygame3.love.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jerry.guan on 4/11/2016.
 */
public class LoverAdapter extends FragmentPagerAdapter {

    String[] titles = {"当前班级", "历史总榜"};
    List<Fragment> fragments;

    public LoverAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
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
