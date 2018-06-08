/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.eventModel;

import com.softtek.lai.module.home.model.HomeInfoModel;

import java.util.List;

/**
 * Created by jerry.guan on 3/24/2016.
 */
public class ActivityEvent {

    public List<HomeInfoModel> activitys;
    //0:刷新 1：加载
    public int flag = 0;

    public ActivityEvent(int flag, List<HomeInfoModel> activitys) {
        this.flag = flag;
        this.activitys = activitys;
    }

    public ActivityEvent(List<HomeInfoModel> activitys) {
        this.activitys = activitys;
    }
}
