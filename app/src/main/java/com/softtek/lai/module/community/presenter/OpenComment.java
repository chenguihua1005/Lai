package com.softtek.lai.module.community.presenter;

import com.softtek.lai.module.community.model.DynamicModel;

/**
 * Created by jerry.guan on 2/7/2017.
 */

public interface OpenComment {

    void doOpen(int itemBottomY, int position, String tag);
    void hiden();
}
