/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ChatContactInfoModel implements Serializable {

    private String AccountId;
    private String Mobile;
    private String UserName;
    private String Gender;
    private String Photo;
    private String SuperiorID;
    private String UserRole;
    private String HXAccountId;
    private String HXAddTime;

    @Override
    public String toString() {
        return "ChatContactInfoModel{" +
                "AccountId='" + AccountId + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Photo='" + Photo + '\'' +
                ", SuperiorID='" + SuperiorID + '\'' +
                ", UserRole='" + UserRole + '\'' +
                ", HXAccountId='" + HXAccountId + '\'' +
                ", HXAddTime='" + HXAddTime + '\'' +
                '}';
    }

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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getSuperiorID() {
        return SuperiorID;
    }

    public void setSuperiorID(String superiorID) {
        SuperiorID = superiorID;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public String getHXAccountId() {
        return HXAccountId;
    }

    public void setHXAccountId(String HXAccountId) {
        this.HXAccountId = HXAccountId;
    }

    public String getHXAddTime() {
        return HXAddTime;
    }

    public void setHXAddTime(String HXAddTime) {
        this.HXAddTime = HXAddTime;
    }
}
