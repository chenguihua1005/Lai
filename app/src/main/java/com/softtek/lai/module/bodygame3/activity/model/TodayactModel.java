package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class TodayactModel {
    private String ActivityId;
    private String ActivityName;
    private String ActivityIcon;
    private int Count;
    private String ActivityStartDate;

    public TodayactModel(String activityId, String activityName, String activityIcon, int count, String activityStartDate) {
        ActivityId = activityId;
        ActivityName = activityName;
        ActivityIcon = activityIcon;
        Count = count;
        ActivityStartDate = activityStartDate;
    }

    public String getActivityStartDate() {
        return ActivityStartDate;
    }

    public void setActivityStartDate(String activityStartDate) {
        ActivityStartDate = activityStartDate;
    }

    public String getActivityId() {
        return ActivityId;
    }

    public void setActivityId(String activityId) {
        ActivityId = activityId;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getActivityIcon() {
        return ActivityIcon;
    }

    public void setActivityIcon(String activityIcon) {
        ActivityIcon = activityIcon;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    @Override
    public String toString() {
        return "TodayactModel{" +
                "ActivityId='" + ActivityId + '\'' +
                ", ActivityName='" + ActivityName + '\'' +
                ", ActivityIcon='" + ActivityIcon + '\'' +
                ", Count=" + Count +
                ", ActivityStartDate='" + ActivityStartDate + '\'' +
                '}';
    }
}
