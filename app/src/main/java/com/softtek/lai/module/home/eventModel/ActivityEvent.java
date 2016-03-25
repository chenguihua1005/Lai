package com.softtek.lai.module.home.eventModel;

import com.softtek.lai.module.home.model.HomeInfo;

import java.util.List;

/**
 * Created by jerry.guan on 3/24/2016.
 */
public class ActivityEvent {

    public List<HomeInfo> activitys;
    //0:刷新 1：加载
    public int flag=0;

    public ActivityEvent(int flag, List<HomeInfo> activitys) {
        this.flag = flag;
        this.activitys = activitys;
    }

    public ActivityEvent(List<HomeInfo> activitys) {
        this.activitys = activitys;
    }
}
