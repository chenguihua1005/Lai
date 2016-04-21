package com.softtek.lai.module.lossweightstory.model;

/**
 * Created by jerry.guan on 4/18/2016.
 * 减重故事详情
 */
public class LogStoryDetailModel {

    private String LossLogId;
    private String UserName;
    private String LogTitle;
    private String AfterWeight;
    private String CreateDate;
    private String LogContent;
    private String imgCollection;
    private String Photo;
    private String priasenum;
    private String ifpriasenum;
    private String UserNames;

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPriasenum() {
        return priasenum;
    }

    public void setPriasenum(String priasenum) {
        this.priasenum = priasenum;
    }

    public String getIfpriasenum() {
        return ifpriasenum;
    }

    public void setIfpriasenum(String ifpriasenum) {
        this.ifpriasenum = ifpriasenum;
    }

    public String getUserNames() {
        return UserNames;
    }

    public void setUserNames(String userNames) {
        UserNames = userNames;
    }

    public String getLossLogId() {
        return LossLogId;
    }

    public void setLossLogId(String lossLogId) {
        LossLogId = lossLogId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLogTitle() {
        return LogTitle;
    }

    public void setLogTitle(String logTitle) {
        LogTitle = logTitle;
    }

    public String getAfterWeight() {
        return AfterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        AfterWeight = afterWeight;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getLogContent() {
        return LogContent;
    }

    public void setLogContent(String logContent) {
        LogContent = logContent;
    }

    public String getImgCollection() {
        return imgCollection;
    }

    public void setImgCollection(String imgCollection) {
        this.imgCollection = imgCollection;
    }
}