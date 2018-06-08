package com.softtek.lai.module.bodygame3.love.model;

/**
 * Created by jessica.zhang on 1/5/2017.
 */

public class LoverModel {
    private int Num;//排名
    private int cou;//转介绍次数
    private long IntroducerId;//-转介绍人Id
    private String UserName;//转介绍人用户名
    private String Photo;//转介绍人头像
    private String CGName;//所属组别

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public int getCou() {
        return cou;
    }

    public void setCou(int cou) {
        this.cou = cou;
    }

    public long getIntroducerId() {
        return IntroducerId;
    }

    public void setIntroducerId(long introducerId) {
        IntroducerId = introducerId;
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

    public String getCGName() {
        return CGName;
    }

    public void setCGName(String CGName) {
        this.CGName = CGName;
    }

    @Override
    public String toString() {
        return "LoverModel{" +
                "Num=" + Num +
                ", cou=" + cou +
                ", IntroducerId=" + IntroducerId +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", CGName='" + CGName + '\'' +
                '}';
    }
}
