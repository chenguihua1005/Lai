package com.softtek.lai.module.community.presenter;

import android.view.View;

import com.softtek.lai.module.community.model.DynamicModel;

/**
 * Created by jerry.guan on 2/7/2017.
 */

public interface OpenComment {

    void doOpen(int position,int itemHeight, String tag);
    void hiden();
}
