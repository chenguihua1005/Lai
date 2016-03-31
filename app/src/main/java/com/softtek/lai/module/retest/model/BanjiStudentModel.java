/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.model;

/**
 * Created by lareina.qiao on 3/24/2016.
 */
public class BanjiStudentModel {
    private String ClassId;
    private String ClassName;
    private String ManagerId;
    private String AccountId;
    private String Mobile;
    private String UserName;
    private String Photo;
    private String TypeDate;
    private String StartDate;
    private String AMStatus;
    private String Weekth;
    private String CurrStart;
    private String CurrEnd;

    public BanjiStudentModel(String classId, String className, String managerId, String accountId, String mobile, String userName, String photo, String typeDate, String startDate, String AMStatus, String weekth, String currStart, String currEnd) {
        ClassId = classId;
        ClassName = className;
        ManagerId = managerId;
        AccountId = accountId;
        Mobile = mobile;
        UserName = userName;
        Photo = photo;
        TypeDate = typeDate;
        StartDate = startDate;
        this.AMStatus = AMStatus;
        Weekth = weekth;
        CurrStart = currStart;
        CurrEnd = currEnd;
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

    public String getManagerId() {
        return ManagerId;
    }

    public void setManagerId(String managerId) {
        ManagerId = managerId;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getTypeDate() {
        return TypeDate;
    }

    public void setTypeDate(String typeDate) {
        TypeDate = typeDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getAMStatus() {
        return AMStatus;
    }

    public void setAMStatus(String AMStatus) {
        this.AMStatus = AMStatus;
    }

    public String getWeekth() {
        return Weekth;
    }

    public void setWeekth(String weekth) {
        Weekth = weekth;
    }

    public String getCurrStart() {
        return CurrStart;
    }

    public void setCurrStart(String currStart) {
        CurrStart = currStart;
    }

    public String getCurrEnd() {
        return CurrEnd;
    }

    public void setCurrEnd(String currEnd) {
        CurrEnd = currEnd;
    }
}
