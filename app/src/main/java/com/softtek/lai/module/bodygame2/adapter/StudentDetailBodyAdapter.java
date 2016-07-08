package com.softtek.lai.module.bodygame2.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by John on 2016/4/2.
 */
public class StudentDetailBodyAdapter extends BaseAdapter{

    private  String[] title={"减重曲线","围度曲线"};
    private List<Fragment> fragmentList;
    private Context context;
    public StudentDetailBodyAdapter(Context context, List<Fragment> fragments) {
//        super(fm);
        this.context=context;
        this.fragmentList=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return title[position];
//    }
}
