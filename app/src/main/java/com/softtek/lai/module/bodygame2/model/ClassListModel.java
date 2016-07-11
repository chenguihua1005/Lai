package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class ClassListModel {
    private String ClassId;
    private String ClassName;
    private String StartDate;

    public ClassListModel(String classId, String className, String startDate) {
        ClassId = classId;
        ClassName = className;
        StartDate = startDate;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
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
}
