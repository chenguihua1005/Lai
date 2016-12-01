package com.softtek.lai.module.bodygame3.conversation.model;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

public class ClassMemberModel {
    private String HXAccountId;
    private int AccountId;//成员ID
    private String UserName;
    private String UserEn;
    private String Mobile;
    private String Photo;
    private String CGId;
    private String CGName;
    private String Role;
    private String AddTime;
    private int IsFriend;//是否是好友（1：是， 0：否）

    public ClassMemberModel(String HXAccountId, int accountId, String userName, String userEn, String mobile, String photo, String CGId, String CGName, String role, String addTime, int isFriend) {
        this.HXAccountId = HXAccountId;
        AccountId = accountId;
        UserName = userName;
        UserEn = userEn;
        Mobile = mobile;
        Photo = photo;
        this.CGId = CGId;
        this.CGName = CGName;
        Role = role;
        AddTime = addTime;
        IsFriend = isFriend;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public int getIsFriend() {
        return IsFriend;
    }

    public void setIsFriend(int isFriend) {
        IsFriend = isFriend;
    }

    public String getHXAccountId() {
        return HXAccountId;
    }

    public void setHXAccountId(String HXAccountId) {
        this.HXAccountId = HXAccountId;
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

    public String getCGId() {
        return CGId;
    }

    public void setCGId(String CGId) {
        this.CGId = CGId;
    }

    public String getCGName() {
        return CGName;
    }

    public void setCGName(String CGName) {
        this.CGName = CGName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }
}
