/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame.presenter;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by lareina.qiao on 3/17/2016.
 */
public interface ITiGuanSai {

    void getTiGuanSai();

    void doGetFuceNum(long id);
    void doGetTips();
    void doGetTipsDetail(long id);
    void doGetTotal();
}
