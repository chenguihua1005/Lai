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
    private String AccountId;
    private String Weight;
    private String Pysical;
    private String Fat;
    private String Circum;
    private String Waistline;
    private String Hiplie;
    private String UpArmGirth;
    private String UpLegGirth;
    private String DoLegGirth;
    private String Image;
    private String ClassId;
    private String Weekth;
    private String CurrStart;
    private String CurrEnd;
    private String IsFirst;
    private String Loss;
    private String TypeDate;
    private String AMStatus;

    @Override
    public String toString() {
        return "RetestAuditModel{" +
                "UserName='" + UserName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Photo='" + Photo + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", InitWeight='" + InitWeight + '\'' +
                ", AccountId='" + AccountId + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Pysical='" + Pysical + '\'' +
                ", Fat='" + Fat + '\'' +
                ", Circum='" + Circum + '\'' +
                ", Waistline='" + Waistline + '\'' +
                ", Hiplie='" + Hiplie + '\'' +
                ", UpArmGirth='" + UpArmGirth + '\'' +
                ", UpLegGirth='" + UpLegGirth + '\'' +
                ", DoLegGirth='" + DoLegGirth + '\'' +
                ", Image='" + Image + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", Weekth='" + Weekth + '\'' +
                ", CurrStart='" + CurrStart + '\'' +
                ", CurrEnd='" + CurrEnd + '\'' +
                ", IsFirst='" + IsFirst + '\'' +
                ", Loss='" + Loss + '\'' +
                ", TypeDate='" + TypeDate + '\'' +
                ", AMStatus='" + AMStatus + '\'' +
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
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getPysical() {
        return Pysical;
    }

    public void setPysical(String pysical) {
        Pysical = pysical;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getCircum() {
        return Circum;
    }

    public void setCircum(String circum) {
        Circum = circum;
    }

    public String getWaistline() {
        return Waistline;
    }

    public void setWaistline(String waistline) {
        Waistline = waistline;
    }

    public String getHiplie() {
        return Hiplie;
    }

    public void setHiplie(String hiplie) {
        Hiplie = hiplie;
    }

    public String getUpArmGirth() {
        return UpArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        UpArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return UpLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        UpLegGirth = upLegGirth;
    }

    public String getDoLegGirth() {
        return DoLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        DoLegGirth = doLegGirth;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
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

    public String getLoss() {
        return Loss;
    }

    public void setLoss(String loss) {
        Loss = loss;
    }

    public String getTypeDate() {
        return TypeDate;
    }

    public void setTypeDate(String typeDate) {
        TypeDate = typeDate;
    }

    public String getAMStatus() {
        return AMStatus;
    }

    public void setAMStatus(String AMStatus) {
        this.AMStatus = AMStatus;
    }

    public RetestAuditModel() {

    }
}
