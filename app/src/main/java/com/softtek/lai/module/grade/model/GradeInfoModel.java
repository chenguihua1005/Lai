/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class GradeInfoModel {

    private String ClassName;
    private String StartDate;
    private String EndDate;
    private String ClassBanner;
    private String PCNum;
    private String SRNum;

    public String getPCNum() {
        return PCNum;
    }

    public void setPCNum(String PCNum) {
        this.PCNum = PCNum;
    }

    public String getSRNum() {
        return SRNum;
    }

    public void setSRNum(String SRNum) {
        this.SRNum = SRNum;
    }

    public String getClassBanner() {
        return ClassBanner;
    }

    public void setClassBanner(String classBanner) {
        ClassBanner = classBanner;
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

    @Override
    public String toString() {
        return "GradeInfoModel{" +
                "ClassName='" + ClassName + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", ClassBanner='" + ClassBanner + '\'' +
                ", PCNum='" + PCNum + '\'' +
                ", SRNum='" + SRNum + '\'' +
                '}';
    }
}
