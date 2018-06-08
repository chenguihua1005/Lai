/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HistorySportModel implements Serializable {

    private String AccountId;
    private String MType;
    private String Mid;
    private String TimeLength;
    private String Trajectory;
    private String total;
    private String createtime;
    private String Kilometre;
    private String calories;
    private String Speed;

    @Override
    public String toString() {
        return "HistorySportModel{" +
                "AccountId='" + AccountId + '\'' +
                ", MType='" + MType + '\'' +
                ", Mid='" + Mid + '\'' +
                ", TimeLength='" + TimeLength + '\'' +
                ", Trajectory='" + Trajectory + '\'' +
                ", total='" + total + '\'' +
                ", createtime='" + createtime + '\'' +
                ", Kilometre='" + Kilometre + '\'' +
                ", calories='" + calories + '\'' +
                '}';
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getMType() {
        return MType;
    }

    public void setMType(String MType) {
        this.MType = MType;
    }

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

    public String getTimeLength() {
        return TimeLength;
    }

    public void setTimeLength(String timeLength) {
        TimeLength = timeLength;
    }

    public String getTrajectory() {
        return Trajectory;
    }

    public void setTrajectory(String trajectory) {
        Trajectory = trajectory;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getKilometre() {
        return Kilometre;
    }

    public void setKilometre(String kilometre) {
        Kilometre = kilometre;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }
}
