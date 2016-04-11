/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.model;

/**
 * Created by lareina.qiao on 3/26/2016.
 */
public class RetestAuditModel {
    private String UserName;
    private String Mobile;
    private String Photo;
    private String ClassName;
    private String StartDate;
    private String InitWeight;
    private String accountId;
    private String weight;
    private String pysical;
    private String fat;
    private String circum;
    private String waistline;
    private String hiplie;
    private String upArmGirth;
    private String upLegGirth;
    private String doLegGirth;
    private String image;
    private String classId;
    private String Weekth;
    private String CurrStart;
    private String CurrEnd;
    private String IsFirst;

    @Override
    public String toString() {
        return "RetestAuditModel{" +
                "UserName='" + UserName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Photo='" + Photo + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", InitWeight='" + InitWeight + '\'' +
                ", accountId='" + accountId + '\'' +
                ", weight='" + weight + '\'' +
                ", pysical='" + pysical + '\'' +
                ", fat='" + fat + '\'' +
                ", circum='" + circum + '\'' +
                ", waistline='" + waistline + '\'' +
                ", hiplie='" + hiplie + '\'' +
                ", upArmGirth='" + upArmGirth + '\'' +
                ", upLegGirth='" + upLegGirth + '\'' +
                ", doLegGirth='" + doLegGirth + '\'' +
                ", image='" + image + '\'' +
                ", classId='" + classId + '\'' +
                ", Weekth='" + Weekth + '\'' +
                ", CurrStart='" + CurrStart + '\'' +
                ", CurrEnd='" + CurrEnd + '\'' +
                ", IsFirst='" + IsFirst + '\'' +
                '}';
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
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

    public String getInitWeight() {
        return InitWeight;
    }

    public void setInitWeight(String initWeight) {
        InitWeight = initWeight;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPysical() {
        return pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCircum() {
        return circum;
    }

    public void setCircum(String circum) {
        this.circum = circum;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHiplie() {
        return hiplie;
    }

    public void setHiplie(String hiplie) {
        this.hiplie = hiplie;
    }

    public String getUpArmGirth() {
        return upArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        this.upArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return upLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        this.upLegGirth = upLegGirth;
    }

    public String getDoLegGirth() {
        return doLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        this.doLegGirth = doLegGirth;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public String getIsFirst() {
        return IsFirst;
    }

    public void setIsFirst(String isFirst) {
        IsFirst = isFirst;
    }

    public RetestAuditModel(String userName, String mobile, String photo, String className, String startDate, String initWeight, String accountId, String weight, String pysical, String fat, String circum, String waistline, String hiplie, String upArmGirth, String upLegGirth, String doLegGirth, String image, String classId, String weekth, String currStart, String currEnd, String isFirst) {
        UserName = userName;
        Mobile = mobile;
        Photo = photo;
        ClassName = className;
        StartDate = startDate;
        InitWeight = initWeight;
        this.accountId = accountId;
        this.weight = weight;
        this.pysical = pysical;
        this.fat = fat;
        this.circum = circum;
        this.waistline = waistline;
        this.hiplie = hiplie;
        this.upArmGirth = upArmGirth;
        this.upLegGirth = upLegGirth;
        this.doLegGirth = doLegGirth;
        this.image = image;
        this.classId = classId;
        Weekth = weekth;
        CurrStart = currStart;
        CurrEnd = currEnd;
        IsFirst = isFirst;
    }

    public RetestAuditModel() {

    }
}
