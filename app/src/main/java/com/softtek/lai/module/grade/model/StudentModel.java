/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.model;

import java.io.Serializable;

/**
 * Created by jerry.guan on 3/22/2016.
 * 学员列表model
 */
public class StudentModel{

    private long AccountId;
    private long ClassId;
    private int OrderNum;
    //按照学员减重
    private String UserName = "";
    private String LossAfter = "0";
    private String LossBefor = "0";
    private String LossWeght = "0";
    //按照学员减重百分比
    private String LossPercent = "0";
    //按照体质排序
    private String Pysical = "0";
    //按照学员腰围排序
    private String WaistlineAfter = "0";
    private String Waistlinebefor = "0";

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

    public String getLossBefor() {
        return LossBefor;
    }

    public void setLossBefor(String lossBefor) {
        LossBefor = lossBefor;
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

    public String getWaistlinebefor() {
        return Waistlinebefor;
    }

    public void setWaistlinebefor(String waistlinebefor) {
        Waistlinebefor = waistlinebefor;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "AccountId=" + AccountId +
                ", ClassIdModel=" + ClassId +
                ", OrderNum=" + OrderNum +
                ", UserName='" + UserName + '\'' +
                ", LossAfter='" + LossAfter + '\'' +
                ", LossBefor='" + LossBefor + '\'' +
                ", LossWeght='" + LossWeght + '\'' +
                ", LossPercent='" + LossPercent + '\'' +
                ", Pysical='" + Pysical + '\'' +
                ", WaistlineAfter='" + WaistlineAfter + '\'' +
                ", Waistlinebefor='" + Waistlinebefor + '\'' +
                '}';
    }
}
