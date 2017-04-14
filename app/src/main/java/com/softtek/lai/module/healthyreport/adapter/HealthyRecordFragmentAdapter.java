package com.softtek.lai.module.healthyreport.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.softtek.lai.module.healthyreport.model.FragmentModel;

import java.util.List;

/**
 * Created by John on 2016/4/12.
 */
public class HealthyRecordFragmentAdapter extends FragmentPagerAdapter{

    private List<FragmentModel> fragmentList;

    public HealthyRecordFragmentAdapter(FragmentManager fm, List<FragmentModel> fragments) {
        super(fm);
        fragmentList=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position).fragment;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).name;
    }
}
