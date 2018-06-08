package com.softtek.lai.module.bodygame3.history.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;


public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
	private ArrayList<Fragment> fragmentsList;
	private FragmentManager fm;

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public MyFragmentPagerAdapter(FragmentManager fm,
								  ArrayList<Fragment> fragments) {
		super(fm);
		this.fm = fm;
		this.fragmentsList = fragments;
	}

	@Override
	public int getCount() {
		return fragmentsList.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentsList.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragments(ArrayList fragments) {
		if (this.fragmentsList != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragmentsList) {
				ft.remove(f);
			}
			ft.commitAllowingStateLoss();
			ft = null;
			fm.executePendingTransactions();
		}
		this.fragmentsList = fragments;
		notifyDataSetChanged();
	}

}
