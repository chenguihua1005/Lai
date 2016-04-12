package com.softtek.lai.module.studentbasedate.model;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public class StudentBaseInfoModel {

    private long AccountId;
    private String banner;
    private long classId;
    private String className;
    private String endDate;
    private String lossAfter;
    private String lossAfterPhoto;
    private String lossBefore;
    private String lossBeforePhoto;
    private float lossTotal;
    private String startDate;
    private String userName;
    private String userPhoto;

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLossAfter() {
        return lossAfter;
    }

    public void setLossAfter(String lossAfter) {
        this.lossAfter = lossAfter;
    }

    public String getLossAfterPhoto() {
        return lossAfterPhoto;
    }

    public void setLossAfterPhoto(String lossAfterPhoto) {
        this.lossAfterPhoto = lossAfterPhoto;
    }

    public String getLossBefore() {
        return lossBefore;
    }

    public void setLossBefore(String lossBefore) {
        this.lossBefore = lossBefore;
    }

    public String getLossBeforePhoto() {
        return lossBeforePhoto;
    }

    public void setLossBeforePhoto(String lossBeforePhoto) {
        this.lossBeforePhoto = lossBeforePhoto;
    }

    public float getLossTotal() {
        return lossTotal;
    }

    public void setLossTotal(float lossTotal) {
        this.lossTotal = lossTotal;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    @Override
    public String toString() {
        return "StudentBaseInfoModel{" +
                "AccountId=" + AccountId +
                ", banner='" + banner + '\'' +
                ", classId=" + classId +
                ", className='" + className + '\'' +
                ", endDate='" + endDate + '\'' +
                ", lossAfter='" + lossAfter + '\'' +
                ", lossAfterPhoto='" + lossAfterPhoto + '\'' +
                ", lossBefore='" + lossBefore + '\'' +
                ", lossBeforePhoto='" + lossBeforePhoto + '\'' +
                ", lossTotal=" + lossTotal +
                ", startDate='" + startDate + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                '}';
    }
}
