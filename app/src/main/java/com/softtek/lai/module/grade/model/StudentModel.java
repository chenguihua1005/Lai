/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/22/2016.
 * 学员列表model
 */
public class StudentModel{

    private long AccountId;
    private long ClassId;
    private int OrderNum;
    //按照学员减重
    private String UserName;
    private String LossAfter;
    private String LossBefore;
    private String LossWeght;
    //按照学员减重百分比
    private String LossPercent ;
    //按照体质排序
    private String Pysical;
    //按照学员腰围排序
    private String WaistlineAfter;
    private String Waistlinebefore;
    private String lossline;
    private String Photo;
    private int IsTest;

    public int getIsTest() {
        return IsTest;
    }

    public void setIsTest(int isTest) {
        IsTest = isTest;
    }

    public String getLossline() {
        return lossline;
    }

    public void setLossline(String lossline) {
        this.lossline = lossline;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public long getClassId() {
        return ClassId;
    }

    public void setClassId(long classId) {
        ClassId = classId;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLossAfter() {
        return LossAfter;
    }

    public void setLossAfter(String lossAfter) {
        LossAfter = lossAfter;
    }

    public String getLossBefore() {
        return LossBefore;
    }

    public void setLossBefore(String lossBefore) {
        LossBefore = lossBefore;
    }

    public String getLossWeght() {
        return LossWeght;
    }

    public void setLossWeght(String lossWeght) {
        LossWeght = lossWeght;
    }

    public String getLossPercent() {
        return LossPercent;
    }

    public void setLossPercent(String lossPercent) {
        LossPercent = lossPercent;
    }

    public String getPysical() {
        return Pysical;
    }

    public void setPysical(String pysical) {
        Pysical = pysical;
    }

    public String getWaistlineAfter() {
        return WaistlineAfter;
    }

    public void setWaistlineAfter(String waistlineAfter) {
        WaistlineAfter = waistlineAfter;
    }

    public String getWaistlinebefore() {
        return Waistlinebefore;
    }

    public void setWaistlinebefore(String waistlinebefore) {
        Waistlinebefore = waistlinebefore;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "AccountId=" + AccountId +
                ", ClassId=" + ClassId +
                ", OrderNum=" + OrderNum +
                ", UserName='" + UserName + '\'' +
                ", LossAfter='" + LossAfter + '\'' +
                ", LossBefore='" + LossBefore + '\'' +
                ", LossWeght='" + LossWeght + '\'' +
                ", LossPercent='" + LossPercent + '\'' +
                ", Pysical='" + Pysical + '\'' +
                ", WaistlineAfter='" + WaistlineAfter + '\'' +
                ", Waistlinebefore='" + Waistlinebefore + '\'' +
                ", lossline='" + lossline + '\'' +
                ", Photo='" + Photo + '\'' +
                ", IsTest=" + IsTest +
                '}';
    }
}
