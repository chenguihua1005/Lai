package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by jia.lu on 5/15/2018.
 */

public class InitialDataModel {

    /**
     * AccountId : 1
     * UserName : 一万
     * Mobile : 13361810000
     * Gender : 1
     * Photo : 2018_03_05/201803051728237237856572.jpg
     * ClassRole : 1
     * CGName : 未分组
     * HasInitMeasure : true
     */

    private int AccountId;
    private String UserName;
    private String Mobile;
    private int Gender;
    private String Photo;
    private int ClassRole;
    private String CGName;
    private boolean HasInitMeasure;

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int AccountId) {
        this.AccountId = AccountId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int ClassRole) {
        this.ClassRole = ClassRole;
    }

    public String getCGName() {
        return CGName;
    }

    public void setCGName(String CGName) {
        this.CGName = CGName;
    }

    public boolean isHasInitMeasure() {
        return HasInitMeasure;
    }

    public void setHasInitMeasure(boolean HasInitMeasure) {
        this.HasInitMeasure = HasInitMeasure;
    }
}
