package com.softtek.lai.module.bodygame3.head.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.softtek.lai.module.bodygame3.head.model.HonorFragmentModel;

import java.util.List;


public class HonorFragmentAdapter extends FragmentPagerAdapter {

    private List<HonorFragmentModel> fragmentList;

    public HonorFragmentAdapter(FragmentManager fm, List<HonorFragmentModel> fragments) {
        super(fm);
        fragmentList = fragments;
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
