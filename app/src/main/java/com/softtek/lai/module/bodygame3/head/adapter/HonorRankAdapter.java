package com.softtek.lai.module.bodygame3.head.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.softtek.lai.module.home.adapter.FragementAdapter;

import java.util.List;

/**
 * Created by lareina.qiao on 11/24/2016.
 */

public class HonorRankAdapter extends FragementAdapter {

    private List<Fragment> fragments;
    private String[] tabtitle={"周排名","月排名","总排名"};

    public HonorRankAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, fragments);
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

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitle[position];
    }
}
