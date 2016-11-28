package com.softtek.lai.module.bodygame3.activity.model;

import com.softtek.lai.module.bodygame3.head.model.ClassModel;

import java.util.List;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class ActivitydataModel {
    private Boolean IsFirst;//是否录入过初始数据
    private List<ActCalendarModel> list_ActCalendar;
    private int ClassRole;//角色
    private int Num;//人数
    private Boolean IsRetest;//是否复测
    private List<TodayactModel> list_Activity;
    private List<ClassModel> list_Class;

    public Boolean getFirst() {
        return IsFirst;
    }

    public void setFirst(Boolean first) {
        IsFirst = first;
    }

    public List<ActCalendarModel> getList_ActCalendar() {
        return list_ActCalendar;
    }

    public void setList_ActCalendar(List<ActCalendarModel> list_ActCalendar) {
        this.list_ActCalendar = list_ActCalendar;
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

    public Boolean getRetest() {
        return IsRetest;
    }

    public void setRetest(Boolean retest) {
        IsRetest = retest;
    }

    public List<TodayactModel> getList_Activity() {
        return list_Activity;
    }

    public void setList_Activity(List<TodayactModel> list_Activity) {
        this.list_Activity = list_Activity;
    }

    public List<ClassModel> getList_Class() {
        return list_Class;
    }

    public void setList_Class(List<ClassModel> list_Class) {
        this.list_Class = list_Class;
    }
}
