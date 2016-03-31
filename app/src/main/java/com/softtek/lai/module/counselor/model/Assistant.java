/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class Assistant implements Serializable {

    private String AccountId;     //SR的ID
    private String Mobile;        //SR的手机号
    private String SrStatus;     //0:不是该班级助教    1:是该班级的助教
    private String UserName;     //用户名
    private String Photo;        //头像路径

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

    public String getSrStatus() {
        return SrStatus;
    }

    public void setSrStatus(String srStatus) {
        SrStatus = srStatus;
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

    @Override
    public String toString() {
        return "Assistant{" +
                "AccountId='" + AccountId + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", SrStatus='" + SrStatus + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                '}';
    }
}
