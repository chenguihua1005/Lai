package com.softtek.lai.module.personalPK.model;

/**
 * Created by jerry.guan on 5/10/2016.
 * 搜索的pk对手
 */
public class PKObjModel {

    private String Photo;
    private long RGAccId;//检索出用户id
    private long RGId;//跑团id
    private String RGName;//跑团名称
    private String UserName;

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public long getRGAccId() {
        return RGAccId;
    }

    public void setRGAccId(long RGAccId) {
        this.RGAccId = RGAccId;
    }

    public long getRGId() {
        return RGId;
    }

    public void setRGId(long RGId) {
        this.RGId = RGId;
    }

    public String getRGName() {
        return RGName;
    }

    public void setRGName(String RGName) {
        this.RGName = RGName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
