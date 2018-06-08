package com.softtek.lai.module.bodygame3.more.model;

/**
 * 成员模型
 * @author jerry.Guan
 *         created by 2016/11/27
 */

public class Member {

    private long AccountId;
    private String UserName;
    private int ClassRole;
    private String Mobile;
    private String Photo;
    private String CGName;
    private String CGId;
    private String HxAccountId;

    public String getHxAccountId() {
        return HxAccountId;
    }

    public void setHxAccountId(String hxAccountId) {
        HxAccountId = hxAccountId;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long AccountId) {
        this.AccountId = AccountId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int ClassRole) {
        this.ClassRole = ClassRole;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public String getCGName() {
        return CGName;
    }

    public void setCGName(String CGName) {
        this.CGName = CGName;
    }

    public String getCGId() {
        return CGId;
    }

    public void setCGId(String CGId) {
        this.CGId = CGId;
    }
}
