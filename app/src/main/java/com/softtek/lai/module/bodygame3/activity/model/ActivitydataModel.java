package com.softtek.lai.module.bodygame3.activity.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.softtek.lai.module.bodygame3.head.model.ClassModel;

import java.util.List;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class ActivitydataModel  {
    private int IsFirst;//是否录入过初始数据
    private List<ActCalendarModel> list_ActCalendar;
    private int ClassRole;//角色
    private int Num;//人数
    private int IsRetest;//是否复测
    private int RetestStatus;//
    private List<TodayactModel> list_Activity;
    private List<ClassModel> list_Class;
    private int Weekth;
    private int HasInitMeasuredData;




    public ActivitydataModel() {

    }

    @Override
    public String toString() {
        return "ActivitydataModel{" +
                "IsFirst=" + IsFirst +
                ", list_ActCalendar=" + list_ActCalendar +
                ", ClassRole=" + ClassRole +
                ", Num=" + Num +
                ", IsRetest=" + IsRetest +
                ", RetestStatus=" + RetestStatus +
                ", list_Activity=" + list_Activity +
                ", list_Class=" + list_Class +
                ", Weekth=" + Weekth +
                ", HasInitMeasuredData=" + HasInitMeasuredData +
                '}';
    }

    public int getHasInitMeasuredData() {
        return HasInitMeasuredData;
    }

    public void setHasInitMeasuredData(int hasInitMeasuredData) {
        HasInitMeasuredData = hasInitMeasuredData;
    }

    public int getRetestStatus() {
        return RetestStatus;
    }

    public void setRetestStatus(int retestStatus) {
        RetestStatus = retestStatus;
    }

    public int getWeekth() {
        return Weekth;
    }

    public void setWeekth(int weekth) {
        Weekth = weekth;
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

    public int getIsFirst() {
        return IsFirst;
    }

    public void setIsFirst(int isFirst) {
        IsFirst = isFirst;
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

    public List<ClassModel> getList_Class() {
        return list_Class;
    }

    public void setList_Class(List<ClassModel> list_Class) {
        this.list_Class = list_Class;
    }


}
