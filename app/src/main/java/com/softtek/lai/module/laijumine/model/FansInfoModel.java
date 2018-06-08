package com.softtek.lai.module.laijumine.model;

/**
 * Created by lareina.qiao on 2/13/2017.
 */

public class FansInfoModel {
    private String AccountId;//粉丝id
    private int IsFocus;//是否关注
    private String UserName;//粉丝用户名
    private String Photo;//粉丝头像
    private String ThPhoto;//头像缩略图
    private String Signature;//粉丝个人签名

    public int getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(int isFocus) {
        IsFocus = isFocus;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
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

    public String getThPhoto() {
        return ThPhoto;
    }

    public void setThPhoto(String thPhoto) {
        ThPhoto = thPhoto;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
