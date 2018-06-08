/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.group.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class PraiseChallengeModel implements Serializable {

    private String AllPCnt;       //总点赞数
    private String BMobile;
    private String BPCnt;
    private String BPhoto;
    private String BUserName;
    private String ChipType;
    private String End;
    private String Mobile;
    private String PCnt;
    private String PKId;
    private String UserPhoto;
    private String Start;
    private String UserName;

    public String getAllPCnt() {
        return AllPCnt;
    }

    public void setAllPCnt(String allPCnt) {
        AllPCnt = allPCnt;
    }

    public String getBMobile() {
        return BMobile;
    }

    public void setBMobile(String BMobile) {
        this.BMobile = BMobile;
    }

    public String getBPCnt() {
        return BPCnt;
    }

    public void setBPCnt(String BPCnt) {
        this.BPCnt = BPCnt;
    }

    public String getBPhoto() {
        return BPhoto;
    }

    public void setBPhoto(String BPhoto) {
        this.BPhoto = BPhoto;
    }

    public String getBUserName() {
        return BUserName;
    }

    public void setBUserName(String BUserName) {
        this.BUserName = BUserName;
    }

    public String getChipType() {
        return ChipType;
    }

    public void setChipType(String chipType) {
        ChipType = chipType;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPCnt() {
        return PCnt;
    }

    public void setPCnt(String PCnt) {
        this.PCnt = PCnt;
    }

    public String getPKId() {
        return PKId;
    }

    public void setPKId(String PKId) {
        this.PKId = PKId;
    }


    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public String toString() {
        return "PraiseChallengeModel{" +
                "AllPCnt='" + AllPCnt + '\'' +
                ", BMobile='" + BMobile + '\'' +
                ", BPCnt='" + BPCnt + '\'' +
                ", BPhoto='" + BPhoto + '\'' +
                ", BUserName='" + BUserName + '\'' +
                ", ChipType='" + ChipType + '\'' +
                ", End='" + End + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", PCnt='" + PCnt + '\'' +
                ", PKId='" + PKId + '\'' +
                ", UserPhoto='" + UserPhoto + '\'' +
                ", Start='" + Start + '\'' +
                ", UserName='" + UserName + '\'' +
                '}';
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }
}
