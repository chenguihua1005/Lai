/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class AssistantDetailInfoModel implements Serializable {

    private String Mrate;        //复测率
    private String TotalWeight;        //总减斤数
    private String num;        //服务顾客数
    private String Mobile;     //助教电话
    private String Photo;        //头像路径
    private String UserName;     //助教名字
    private String AccountId;        //顾问Id

    public String getMrate() {
        return Mrate;
    }

    public void setMrate(String mrate) {
        Mrate = mrate;
    }

    public String getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        TotalWeight = totalWeight;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    @Override
    public String toString() {
        return "AssistantDetailInfoModel{" +
                "Mrate='" + Mrate + '\'' +
                ", TotalWeight='" + TotalWeight + '\'' +
                ", num='" + num + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Photo='" + Photo + '\'' +
                ", UserName='" + UserName + '\'' +
                ", AccountId='" + AccountId + '\'' +
                '}';
    }
}
