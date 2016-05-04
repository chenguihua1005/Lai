package com.softtek.lai.module.studetail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by John on 2016/4/2.
 */
public class StudentDetailFragmentAdapter extends FragmentPagerAdapter{

    private  String[] title={"减重曲线","围度曲线"};
    private List<Fragment> fragmentList;

    public StudentDetailFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
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
        return title[position];
    }
}
