package com.softtek.lai.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.softtek.lai.module.home.view.ActivityRecordFragment;
import com.softtek.lai.module.home.view.ProductInfoFragment;
import com.softtek.lai.module.home.view.SaleInfoFragment;

/**
 * Created by jerry.guan on 3/23/2016.
 */
public class FragementAdapter extends FragmentPagerAdapter{

    private String[] titles={"活动推荐","产品信息","促销信息"};
    private Fragment[] frament={new ActivityRecordFragment(),new ProductInfoFragment(),new SaleInfoFragment()};

    public FragementAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return frament[position];
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
