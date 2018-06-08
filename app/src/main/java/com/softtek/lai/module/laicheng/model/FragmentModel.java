package com.softtek.lai.module.laicheng.model;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;

/**
 * Created by shelly.xu on 4/5/2017.
 */

public class FragmentModel {

    private String name;
    private Fragment fragment;

    public FragmentModel( String name, Fragment fragment) {
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
