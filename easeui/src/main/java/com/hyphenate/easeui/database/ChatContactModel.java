package com.hyphenate.easeui.database;

import java.io.Serializable;

/**
 * Created by jessica.zhang on 11/25/2016.
 */

public class ChatContactModel implements Serializable {

    private String Mobile;
    private String UserName;
    private String UserEn;
    private int Gender;//0 女 1 男
    private String Photo;
    private int UserRole;
    private String HXAccountId;
    private String AccpetTime;

    private String Certification;//
    private long AccountId;//好友账号
    private String AFriendId;//好友关系id

    public String getAFriendId() {
        return AFriendId;
    }

    public void setAFriendId(String AFriendId) {
        this.AFriendId = AFriendId;
    }

    public ChatContactModel(String mobile, String userName, String userEn, int gender, String photo, int userRole, String HXAccountId, String accpetTime, String certification, long accountId, String AFriendId) {
        Mobile = mobile;
        UserName = userName;
        UserEn = userEn;
        Gender = gender;
        Photo = photo;
        UserRole = userRole;
        this.HXAccountId = HXAccountId;
        AccpetTime = accpetTime;
        Certification = certification;
        AccountId = accountId;
        this.AFriendId = AFriendId;
    }

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String certification) {
        Certification = certification;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
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

    public String getUserEn() {
        return UserEn;
    }

    public void setUserEn(String userEn) {
        UserEn = userEn;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public int getUserRole() {
        return UserRole;
    }

    public void setUserRole(int userRole) {
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
