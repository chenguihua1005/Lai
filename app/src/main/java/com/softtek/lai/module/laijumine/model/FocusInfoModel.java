package com.softtek.lai.module.laijumine.model;

/**
 * Created by lareina.qiao on 2/13/2017.
 */

public class FocusInfoModel {
    private String FocusAccountId;//关注用户id
    private String UserName;//用户名
    private String Photo;//头像
    private String ThPhoto;//头像缩略图
    private String Signature;//个性签名
    private boolean IsFocus;

    public boolean isFocus() {
        return IsFocus;
    }

    public void setFocus(boolean focus) {
        IsFocus = focus;
    }

    @Override
    public String toString() {
        return "FocusInfoModel{" +
                "FocusAccountId='" + FocusAccountId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", ThPhoto='" + ThPhoto + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }

    public String getFocusAccountId() {
        return FocusAccountId;
    }

    public void setFocusAccountId(String focusAccountId) {
        FocusAccountId = focusAccountId;
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
