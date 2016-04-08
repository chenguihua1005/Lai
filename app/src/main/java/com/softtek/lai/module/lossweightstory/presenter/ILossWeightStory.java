package com.softtek.lai.module.lossweightstory.presenter;

import android.widget.CheckBox;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public interface ILossWeightStory {

    void  getLossWeightLogForClass(long accoundId);

    void doZan(long accountId, long logId, CheckBox zan);
}
