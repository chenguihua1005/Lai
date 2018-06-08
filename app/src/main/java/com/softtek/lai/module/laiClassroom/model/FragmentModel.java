package com.softtek.lai.module.laiClassroom.model;

import android.support.v4.app.Fragment;

/**
 * Created by jerry.guan on 3/8/2017.
 */

public class FragmentModel {

    private String name;
    private Fragment fragment;

    public FragmentModel(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
