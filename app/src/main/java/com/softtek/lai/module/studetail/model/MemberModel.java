/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.model;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class MemberModel {

    private int AccountId;  //学员ID
    private int ClassId;    //班级ID
    private int OrderNum;   //排序(此接口不使用该字段)
    private String UserName;//用户名
    private String Photo;
    private String AfterImg;//减重前图片
    private String BeforImg; //减重后图片
    private int LogCount;   //日志篇数
    private String LossAfter;//	减重前重量
    private String LossBefor;//	减重后重量
    private String LossWeight;//减重斤数
    private String Mobile;

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public int getClassId() {
        return ClassId;
    }

    public void setClassId(int classId) {
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

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getAfterImg() {
        return AfterImg;
    }

    public void setAfterImg(String afterImg) {
        AfterImg = afterImg;
    }

    public String getBeforImg() {
        return BeforImg;
    }

    public void setBeforImg(String beforImg) {
        BeforImg = beforImg;
    }

    public int getLogCount() {
        return LogCount;
    }

    public void setLogCount(int logCount) {
        LogCount = logCount;
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

    public String getLossWeight() {
        return LossWeight;
    }

    public void setLossWeight(String lossWeight) {
        LossWeight = lossWeight;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    @Override
    public String toString() {
        return "MemberModel{" +
                "AccountId=" + AccountId +
                ", ClassIdModel=" + ClassId +
                ", OrderNum=" + OrderNum +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", AfterImg='" + AfterImg + '\'' +
                ", BeforImg='" + BeforImg + '\'' +
                ", LogCount=" + LogCount +
                ", LossAfter='" + LossAfter + '\'' +
                ", LossBefor='" + LossBefor + '\'' +
                ", LossWeight='" + LossWeight + '\'' +
                ", Mobile='" + Mobile + '\'' +
                '}';
    }
}
