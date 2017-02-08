package com.softtek.lai.module.community.presenter;

import com.softtek.lai.module.community.model.Comment;
import com.softtek.lai.module.community.model.DynamicModel;

/**
 * Created by jerry.guan on 2/7/2017.
 */

public interface SendCommend {

    void doScroll(int position,int y);

    void doSend(int position,Comment comment);
}
