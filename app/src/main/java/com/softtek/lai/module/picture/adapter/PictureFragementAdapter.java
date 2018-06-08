package com.softtek.lai.module.picture.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jerry.guan on 5/5/2016.
 */
public class PictureFragementAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    public PictureFragementAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
