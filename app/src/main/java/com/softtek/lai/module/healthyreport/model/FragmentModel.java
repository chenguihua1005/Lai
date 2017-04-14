package com.softtek.lai.module.healthyreport.model;

import android.support.v4.app.Fragment;

/**
 * Created by jerry.guan on 4/12/2017.
 */

public class FragmentModel {

    public String name;
    public Fragment fragment;

    public FragmentModel(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }
}
