package com.softtek.lai.module.bodygame3.conversation.model;

import java.io.Serializable;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

public class ClassMemberModel implements Serializable {
    private String HXAccountId;
    private long AccountId;//成员ID
    private String UserName;
    private String UserEn;
    private String Mobile;
    private String Photo;
    private String CGId;//组Id
    private String CGName;//组名
    private String Role;
    private String AddTime;
    private int IsFriend;//是否是好友（1：是， 0：否）
    private String AFriendId;//班级好友关系ID

    public ClassMemberModel() {
    }

    public ClassMemberModel(String HXAccountId, long accountId, String userName, String userEn, String mobile, String photo, String CGId, String CGName, String role, String addTime, int isFriend, String AFriendId) {
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
        this.AFriendId = AFriendId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public String getAFriendId() {
        return AFriendId;
    }

    public void setAFriendId(String AFriendId) {
        this.AFriendId = AFriendId;
    }

    public long getAccountId() {
        return AccountId;
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

    @Override
    public String toString() {
        return "ClassMemberModel{" +
                "HXAccountId='" + HXAccountId + '\'' +
                ", AccountId=" + AccountId +
                ", UserName='" + UserName + '\'' +
                ", UserEn='" + UserEn + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Photo='" + Photo + '\'' +
                ", CGId='" + CGId + '\'' +
                ", CGName='" + CGName + '\'' +
                ", Role='" + Role + '\'' +
                ", AddTime='" + AddTime + '\'' +
                ", IsFriend=" + IsFriend +
                ", AFriendId='" + AFriendId + '\'' +
                '}';
    }
}
