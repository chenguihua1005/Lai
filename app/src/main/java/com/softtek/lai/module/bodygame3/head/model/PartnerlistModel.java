package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by Lenovo-G400 on 2016/12/1.
 */

public class PartnerlistModel {
    private int AccountId;
    private String Photo;
    private String ThPhoto;
    private String UserName;
    private String Mobile;
    private String Certification;
    private String ClassRole;

    public PartnerlistModel(int accountId, String photo, String thPhoto, String userName, String mobile, String certification, String classRole) {
        AccountId = accountId;
        Photo = photo;
        ThPhoto = thPhoto;
        UserName = userName;
        Mobile = mobile;
        Certification = certification;
        ClassRole = classRole;
    }

    public String getClassRole() {
        return ClassRole;
    }

    public void setClassRole(String classRole) {
        ClassRole = classRole;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getThPhoto() {
        return ThPhoto;
    }

    public void setThPhoto(String thPhoto) {
        ThPhoto = thPhoto;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String certification) {
        Certification = certification;
    }
}
