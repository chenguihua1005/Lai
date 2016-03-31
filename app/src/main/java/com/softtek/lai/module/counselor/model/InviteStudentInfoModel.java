/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class InviteStudentInfoModel implements Serializable {

    private String Mobile;     //助教电话
    private String UserName;     //助教名字
    private String Photo;          //图片路径
    private String AccountId;    //学员Id
    private String isinvite;    //1为已发送0为未邀请

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

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getIsinvite() {
        return isinvite;
    }

    public void setIsinvite(String isinvite) {
        this.isinvite = isinvite;
    }

    @Override
    public String toString() {
        return "InviteStudentInfoModel{" +
                "Mobile='" + Mobile + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", AccountId='" + AccountId + '\'' +
                ", isinvite='" + isinvite + '\'' +
                '}';
    }
}
