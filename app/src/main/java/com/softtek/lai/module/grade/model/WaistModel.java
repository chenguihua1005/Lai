/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/22/2016.
 * 学员列表model
 */
public class WaistModel {

    private long AccountId;
    private long ClassId;
    private int OrderNum;
    //按照学员减重
    private String UserName = "";
    //按照学员腰围排序
    private String WaistlineAfter = "0";
    private String Waistlinebefor = "0";
    private String Lossline;
    private String Photo;

    public String getLossline() {
        return Lossline;
    }

    public void setLossline(String lossline) {
        this.Lossline = lossline;
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

    public String getWaistlineAfter() {
        return WaistlineAfter;
    }

    public void setWaistlineAfter(String waistlineAfter) {
        WaistlineAfter = waistlineAfter;
    }

    public String getWaistlinebefor() {
        return Waistlinebefor;
    }

    public void setWaistlinebefor(String waistlinebefor) {
        Waistlinebefor = waistlinebefor;
    }

    @Override
    public String toString() {
        return "WaistModel{" +
                "AccountId=" + AccountId +
                ", ClassId=" + ClassId +
                ", OrderNum=" + OrderNum +
                ", UserName='" + UserName + '\'' +
                ", WaistlineAfter='" + WaistlineAfter + '\'' +
                ", Waistlinebefor='" + Waistlinebefor + '\'' +
                ", Lossline='" + Lossline + '\'' +
                ", Photo='" + Photo + '\'' +
                '}';
    }
}
