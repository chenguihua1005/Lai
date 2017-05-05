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
    private String RoleName;
    private String nickname;
    private String gender;//0表示男 1表示女 如果是2那就表示未填写
    private String weight;
    private String hight;
    private String Photo;
    private String Certification;
    private String CertTime;
    private String Mobile;
    private String birthday;
    private String IsJoin;//是否加入跑团
    private String TodayStepCnt;//当天最新步数
    private String IsCreatInfo;//该用户是否创建过档案
    private String HXAccountId;//若为空则还未注册
    private String HasEmchat;//0：未注册，1：已注册
    private int HasThClass;//0无班级，1有班级
    private int DoingClass;//0没有进行中的班级,1有
    private boolean exit;


    public boolean getExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public String getHight() {
        return hight==null?"":hight;
    }

    public void setHight(String height) {
        this.hight = height;
    }

    public String getBirthday() {
        return birthday==null?"":birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRoleName() {
        return RoleName==null?"":RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getHXAccountId() {
        return HXAccountId==null?"":HXAccountId;
    }

    public void setHXAccountId(String HXAccountId) {
        this.HXAccountId = HXAccountId;
    }

    public String getHasEmchat() {
        return HasEmchat==null?"0":HasEmchat;
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
        return Mobile==null?"":Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPhoto() {
        return Photo==null?"":Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getCertification() {
        return Certification==null?"":Certification;
    }

    public void setCertification(String certification) {
        Certification = certification;
    }

    public String getCertTime() {
        return CertTime==null?"":CertTime;
    }

    public void setCertTime(String certTime) {
        CertTime = certTime;
    }

    public String getToken() {
        return token==null?"":token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid==null?"":userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserrole() {
        return userrole==null?"":userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getNickname() {
        return nickname==null?"":nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    //默认行为不可改0=男 1=女 如果性别是2则表示未填写 未填写默认显示女
    public String getGender() {
        return Integer.parseInt(gender)==2?"1":gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isHasGender() {
        return Integer.parseInt(gender)==2?false:true;
    }

    public String getWeight() {
        return weight==null?"":weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public String getIsJoin() {
        return IsJoin==null?"0":IsJoin;
    }

    public void setIsJoin(String isJoin) {
        IsJoin = isJoin;
    }

    public String getTodayStepCnt() {
        return TodayStepCnt==null?"0":TodayStepCnt;
    }

    public void setTodayStepCnt(String todayStepCnt) {
        TodayStepCnt = todayStepCnt;
    }

    public String getIsCreatInfo() {
        return IsCreatInfo==null?"":IsCreatInfo;
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

    public int getDoingClass() {
        return DoingClass;
    }

    public void setDoingClass(int doingClass) {
        DoingClass = doingClass;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "token='" + token + '\'' +
                ", userid='" + userid + '\'' +
                ", userrole='" + userrole + '\'' +
                ", RoleName='" + RoleName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", weight='" + weight + '\'' +
                ", hight='" + hight + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Certification='" + Certification + '\'' +
                ", CertTime='" + CertTime + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", birthday='" + birthday + '\'' +
                ", IsJoin='" + IsJoin + '\'' +
                ", TodayStepCnt='" + TodayStepCnt + '\'' +
                ", IsCreatInfo='" + IsCreatInfo + '\'' +
                ", HXAccountId='" + HXAccountId + '\'' +
                ", HasEmchat='" + HasEmchat + '\'' +
                ", HasThClass=" + HasThClass +
                ", DoingClass=" + DoingClass +
                ", exit=" + exit +
                ", hasGender=" + hasGender +
                '}';
    }
}
