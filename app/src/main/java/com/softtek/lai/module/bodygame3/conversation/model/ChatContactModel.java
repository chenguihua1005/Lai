package com.softtek.lai.module.bodygame3.conversation.model;

import java.io.Serializable;

/**
 * Created by jessica.zhang on 11/25/2016.
 */

public class ChatContactModel implements Serializable{
    private String Mobile;
    private String UserName;
    private String UserEn;
    private String Gender;
    private String Photo;
    private String UserRole;
    private String HXAccountId;
    private String AccpetTime;

    public ChatContactModel(String mobile, String userName, String userEn, String gender, String photo, String userRole, String HXAccountId, String accpetTime) {
        Mobile = mobile;
        UserName = userName;
        UserEn = userEn;
        Gender = gender;
        Photo = photo;
        UserRole = userRole;
        this.HXAccountId = HXAccountId;
        AccpetTime = accpetTime;
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

    public String getUserEn() {
        return UserEn;
    }

    public void setUserEn(String userEn) {
        UserEn = userEn;
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

    public String getAccpetTime() {
        return AccpetTime;
    }

    public void setAccpetTime(String accpetTime) {
        AccpetTime = accpetTime;
    }
}
