package com.softtek.lai.module.assistant.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MarchInfo implements Serializable {

    private String rnum;     //排名
    private String BeforeWight;     //减重前斤数
    private String AfterWeight;        //减重后斤数
    private String LoseWeight;        //减重斤数
    private String UserName;        //昵称
    private String AccountId;        //Id
    private String photo;        //图片路径

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getBeforeWight() {
        return BeforeWight;
    }

    public void setBeforeWight(String beforeWight) {
        BeforeWight = beforeWight;
    }

    public String getAfterWeight() {
        return AfterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        AfterWeight = afterWeight;
    }

    public String getLoseWeight() {
        return LoseWeight;
    }

    public void setLoseWeight(String loseWeight) {
        LoseWeight = loseWeight;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "MarchInfo{" +
                "rnum='" + rnum + '\'' +
                ", BeforeWight='" + BeforeWight + '\'' +
                ", AfterWeight='" + AfterWeight + '\'' +
                ", LoseWeight='" + LoseWeight + '\'' +
                ", UserName='" + UserName + '\'' +
                ", AccountId='" + AccountId + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
