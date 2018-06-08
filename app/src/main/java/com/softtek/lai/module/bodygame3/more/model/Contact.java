package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jerry.guan on 11/22/2016.
 * 小伙伴联系人模型
 */

public class Contact {

    private String Photo;
    private String UserName;
    private long AccountId;
    private String Mobile;
    private String Certification;
    private String UserEn;
    private String HXAccountId;

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long AccountId) {
        this.AccountId = AccountId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String Certification) {
        this.Certification = Certification;
    }

    public String getUserEn() {
        return UserEn;
    }

    public void setUserEn(String UserEn) {
        this.UserEn = UserEn;
    }

    public String getHXAccountId() {
        return HXAccountId;
    }

    public void setHXAccountId(String HXAccountId) {
        this.HXAccountId = HXAccountId;
    }
}
