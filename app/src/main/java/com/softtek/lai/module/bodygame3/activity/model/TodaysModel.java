package com.softtek.lai.module.bodygame3.activity.model;

import java.util.List;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class TodaysModel {
    private int ClassRole;
    private int Num;
    private int IsRetest;
    private int RetestStatus;
    private int Weekth;
    private List<TodayactModel> list_Activity;

    public int getWeekth() {
        return Weekth;
    }

    public void setWeekth(int weekth) {
        Weekth = weekth;
    }

    public int getRetestStatus() {
        return RetestStatus;
    }

    public void setRetestStatus(int retestStatus) {
        RetestStatus = retestStatus;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int classRole) {
        ClassRole = classRole;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public int getIsRetest() {
        return IsRetest;
    }

    public void setIsRetest(int isRetest) {
        IsRetest = isRetest;
    }

    public List<TodayactModel> getList_Activity() {
        return list_Activity;
    }

    public void setList_Activity(List<TodayactModel> list_Activity) {
        this.list_Activity = list_Activity;
    }
}
