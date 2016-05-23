package com.softtek.lai.module.bodygamest.model;

/**
 * Created by jerry.guan on 4/16/2016.
 */
public class HistoryClassModel {

    private String ClassId;
    private String ClassName;
    private String StartDate;
    private String EndDate;
    private String LoseWeight;

    @Override
    public String toString() {
        return "HistoryClassModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", LoseWeight='" + LoseWeight + '\'' +
                '}';
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

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getLoseWeight() {
        return LoseWeight;
    }

    public void setLoseWeight(String loseWeight) {
        LoseWeight = loseWeight;
    }
}
