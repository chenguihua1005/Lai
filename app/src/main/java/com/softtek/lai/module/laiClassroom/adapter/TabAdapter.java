package com.softtek.lai.module.laiClassroom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.softtek.lai.module.laiClassroom.model.FragmentModel;

import java.util.List;

/**
 * Created by jerry.guan on 3/8/2017.
 */

public class TabAdapter extends FragmentPagerAdapter{

    private List<FragmentModel> fragments;

    public TabAdapter(FragmentManager fm,List<FragmentModel> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getName();
    }
}
