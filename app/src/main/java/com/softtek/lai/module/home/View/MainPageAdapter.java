package com.softtek.lai.module.home.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.snowdream.android.util.Log;

/**
 * Created by jerry.guan on 3/9/2016.
 */
public class MainPageAdapter extends FragmentPagerAdapter{

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new OneFragment();
            case 1:return new TwoFragment();
            case 2:return new ThreeFragment();
            case 3:return new FourFragment();
            case 4:return new FourFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
