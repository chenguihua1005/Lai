/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/23/2016.
 */
public class SRInfoModel {

    private String UserName;
    private String Mobile;
    private String num;
    private String TotalWight;
    private String rtest;
    private String IsInvited;
    private String Photo;


    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTotalWight() {
        return TotalWight;
    }

    public void setTotalWight(String totalWight) {
        TotalWight = totalWight;
    }

    public String getRtest() {
        return rtest;
    }

    public void setRtest(String rtest) {
        this.rtest = rtest;
    }

    public String getIsInvited() {
        return IsInvited;
    }

    public void setIsInvited(String isInvited) {
        IsInvited = isInvited;
    }

    @Override
    public String toString() {
        return "SRInfoModel{" +
                "UserName='" + UserName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", num='" + num + '\'' +
                ", TotalWight='" + TotalWight + '\'' +
                ", rtest='" + rtest + '\'' +
                ", IsInvited='" + IsInvited + '\'' +
                '}';
    }
}
