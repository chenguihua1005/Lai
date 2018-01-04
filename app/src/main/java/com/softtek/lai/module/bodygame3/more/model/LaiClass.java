package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jerry.guan on 11/18/2016.
 * 班级模型
 */

public class LaiClass {

    private String ClassCode;
    private String ClassName;
    private String StartDate;
    private String GroupName;
    private int cityId;
    private int Target;//0-减重，1-增重
    private String ClubId;
    private String ClassId;

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClubId() {
        return ClubId;
    }

    public void setClubId(String clubId) {
        ClubId = clubId;
    }

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }
}
