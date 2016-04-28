/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/22/2016.
 * 学员列表model
 */
public class LossWeightPerModel {

    private long AccountId;
    private long ClassId;
    private int OrderNum;
    //按照学员减重

    private String UserName = "";
    private String LossAfter = "0";
    private String LossBefor = "0";
    //按照学员减重百分比
    private String LossPercent = "0";

    private String Photo;

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

    public String getLossBefor() {
        return LossBefor;
    }

    public void setLossBefor(String lossBefor) {
        LossBefor = lossBefor;
    }


    public String getLossPercent() {
        return LossPercent;
    }

    public void setLossPercent(String lossPercent) {
        LossPercent = lossPercent;
    }


    @Override
    public String toString() {
        return "LossWeightPerModel{" +
                "AccountId=" + AccountId +
                ", ClassId=" + ClassId +
                ", OrderNum=" + OrderNum +
                ", UserName='" + UserName + '\'' +
                ", LossAfter='" + LossAfter + '\'' +
                ", LossBefor='" + LossBefor + '\'' +
                ", LossPercent='" + LossPercent + '\'' +
                ", Photo='" + Photo + '\'' +
                '}';
    }
}
