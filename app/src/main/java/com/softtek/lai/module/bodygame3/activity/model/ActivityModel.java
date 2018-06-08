package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by shelly.xu on 12/1/2016.
 */

public class ActivityModel {
    private long AccountId;
    private String ClassId;
    private String Title;
    private int ClassActivityId;
    private Boolean IsWholeDay;
    private String StartTime;
    private String Content;

    @Override
    public String toString() {
        return "ActivityModel{" +
                "AccountId=" + AccountId +
                ", ClassId='" + ClassId + '\'' +
                ", Title='" + Title + '\'' +
                ", ClassActivityId=" + ClassActivityId +
                ", IsWholeDay=" + IsWholeDay +
                ", StartTime='" + StartTime + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getClassActivityId() {
        return ClassActivityId;
    }

    public void setClassActivityId(int classActivityId) {
        ClassActivityId = classActivityId;
    }

    public Boolean getWholeDay() {
        return IsWholeDay;
    }

    public void setWholeDay(Boolean wholeDay) {
        IsWholeDay = wholeDay;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
