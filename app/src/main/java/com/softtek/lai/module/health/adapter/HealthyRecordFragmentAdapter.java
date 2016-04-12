package com.softtek.lai.module.health.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by John on 2016/4/12.
 */
public class HealthyRecordFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragmentList;
    private String[] titles={"体重","体脂","内脂","胸围",
            "腰围","臀围","上臀围","大腿围","小腿围"};

    public HealthyRecordFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        fragmentList=fragments;
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
