/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.model;

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
    private String IsJoin;//是否加入跑团
    private String TodayStepCnt;//当天最新步数
    private String IsCreatInfo;//该用户是否创建过档案
    private String HXAccountId;//若为空则还未注册
    private String HasEmchat;//0：未注册，1：已注册
    private int HasThClass;//0无班级，1有班级
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
                ", IsJoin='" + IsJoin + '\'' +
                ", TodayStepCnt='" + TodayStepCnt + '\'' +
                ", IsCreatInfo='" + IsCreatInfo + '\'' +
                ", HXAccountId='" + HXAccountId + '\'' +
                ", HasEmchat='" + HasEmchat + '\'' +
                ", hasGender=" + hasGender +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHXAccountId() {
        return HXAccountId;
    }

    public void setHXAccountId(String HXAccountId) {
        this.HXAccountId = HXAccountId;
    }

    public String getHasEmchat() {
        return HasEmchat;
    }

    public void setHasEmchat(String hasEmchat) {
        HasEmchat = hasEmchat;
    }

    public void setHasGender(boolean hasGender) {
        this.hasGender = hasGender;
    }

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
    //默认行为不可改
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

    public String getIsJoin() {
        return IsJoin;
    }

    public void setIsJoin(String isJoin) {
        IsJoin = isJoin;
    }

    public String getTodayStepCnt() {
        return TodayStepCnt;
    }

    public void setTodayStepCnt(String todayStepCnt) {
        TodayStepCnt = todayStepCnt;
    }

    public String getIsCreatInfo() {
        return IsCreatInfo;
    }

    public void setIsCreatInfo(String isCreatInfo) {
        IsCreatInfo = isCreatInfo;
    }

    public int getHasThClass() {
        return HasThClass;
    }

    public void setHasThClass(int hasThClass) {
        HasThClass = hasThClass;
    }
}
