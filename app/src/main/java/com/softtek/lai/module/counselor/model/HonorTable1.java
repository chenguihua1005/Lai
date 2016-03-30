package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HonorTable1 implements Serializable {

    private String rnum;     //全国排名
    private String AccountId;     //用户id
    private String LoseWeight;     //减重斤数
    private String UserName;     //用户昵称
    private String photo;     //头像路径

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "HonorTable1{" +
                "rnum='" + rnum + '\'' +
                ", AccountId='" + AccountId + '\'' +
                ", LoseWeight='" + LoseWeight + '\'' +
                ", UserName='" + UserName + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
