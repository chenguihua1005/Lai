package com.softtek.lai.module.bodygame2pc.model;

/**
 * Created by lareina.qiao on 7/14/2016.
 */
public class StuClmInfoModel {
    private String ClassName;
    private String EndDate;
    private String Photo;
    private String StartDate;
    private String UserName;
    private String afterImage;
    private String afterWeight;
    private String beforeImage;
    private String beforeWeight;
    private String lossPer;
    private String totalLoss;

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAfterImage() {
        return afterImage;
    }

    public void setAfterImage(String afterImage) {
        this.afterImage = afterImage;
    }

    public String getAfterWeight() {
        return afterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        this.afterWeight = afterWeight;
    }

    public String getBeforeImage() {
        return beforeImage;
    }

    public void setBeforeImage(String beforeImage) {
        this.beforeImage = beforeImage;
    }

    public String getBeforeWeight() {
        return beforeWeight;
    }

    public void setBeforeWeight(String beforeWeight) {
        this.beforeWeight = beforeWeight;
    }

    public String getLossPer() {
        return lossPer;
    }

    public void setLossPer(String lossPer) {
        this.lossPer = lossPer;
    }

    public String getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(String totalLoss) {
        this.totalLoss = totalLoss;
    }
}
