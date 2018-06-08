package com.softtek.lai.module.laicheng.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.softtek.lai.module.laicheng.model.FragmentModel;

import java.util.List;

/**
 * Created by shelly.xu on 4/5/2017.
 */

public class BalanceAdapter extends FragmentPagerAdapter {
    private List<FragmentModel> fragmentModels;

    public BalanceAdapter(FragmentManager fm ,List<FragmentModel> fragementModels) {
        super(fm);
        this.fragmentModels = fragementModels;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentModels.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        FragmentModel model=fragmentModels.get(position);
        return model.getName();
    }

    @Override
    public int getCount() {
        return fragmentModels.size();
    }
}
