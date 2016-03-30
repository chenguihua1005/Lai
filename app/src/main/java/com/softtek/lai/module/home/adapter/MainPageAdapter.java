package com.softtek.lai.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.softtek.lai.module.home.view.HealthyFragment;
import com.softtek.lai.module.home.view.HealthyRecordFragment;
import com.softtek.lai.module.home.view.HomeFragment;
import com.softtek.lai.module.home.view.MineFragment;

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
            case 0:return new HomeFragment();
            case 1:return new HealthyFragment();
            case 2:return new HealthyRecordFragment();
            case 3:return new MineFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
