/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.model;

import com.github.snowdream.android.util.Log;

import java.io.Serializable;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class UserModel implements Serializable {
    public static final long serialVersionUID =-4337235045541814883L;
    private String token;

    private String userid;
    private String userrole;
    private String nickname;
    private String gender;
    private String weight;
    private String height;
    private String Photo;
    private String Certification;
    private String CertTime;
    private String Mobile;
    //*********记录用户是否有性别
    private boolean hasGender;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String certification) {
        Certification = certification;
    }

    public String getCertTime() {
        return CertTime;
    }

    public void setCertTime(String certTime) {
        CertTime = certTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return Integer.parseInt(gender)==2?"0":gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isHasGender() {
        return Integer.parseInt(gender)==2?false:true;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "token='" + token + '\'' +
                ", userid='" + userid + '\'' +
                ", userrole='" + userrole + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Certification='" + Certification + '\'' +
                ", CertTime='" + CertTime + '\'' +
                ", Mobile='" + Mobile + '\'' +
                '}';
    }
}
