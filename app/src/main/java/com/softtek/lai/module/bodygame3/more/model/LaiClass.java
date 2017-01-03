package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jerry.guan on 11/18/2016.
 * 班级模型
 */

public class LaiClass {

    private String ClassId;
    private String ClassCode;
    private String ClassName;
    private String StartDate;
    private String ClassGroup;
    private int cityId;
    private long ClassMasterId;
    private String HxGroupId;//环信组Id

    public String getHxGroupId() {
        return HxGroupId;
    }

    public void setHxGroupId(String hxGroupId) {
        HxGroupId = hxGroupId;
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

    public String getClassGroup() {
        return ClassGroup;
    }

    public void setClassGroup(String classGroup) {
        ClassGroup = classGroup;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public long getClassMasterId() {
        return ClassMasterId;
    }

    public void setClassMasterId(long classMasterId) {
        ClassMasterId = classMasterId;
    }
}
