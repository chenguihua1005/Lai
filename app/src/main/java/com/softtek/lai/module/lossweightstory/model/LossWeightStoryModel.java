package com.softtek.lai.module.lossweightstory.model;

/**
 * Created by John on 2016/4/14.
 */
public class LossWeightStoryModel {

    private String UserName;
    private String Photo;
    private String AcBanner;
    private String LossLogId;
    private String CreateDate;
    private String LogTitle;
    private String LogContent;
    private String Priase;
    private String imgCollectionFirst;
    private String imgCollection;
    private String isClicked;
    private String usernameSet;

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

    public String getAcBanner() {
        return AcBanner;
    }

    public void setAcBanner(String acBanner) {
        AcBanner = acBanner;
    }

    public String getLossLogId() {
        return LossLogId;
    }

    public void setLossLogId(String lossLogId) {
        LossLogId = lossLogId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getLogTitle() {
        return LogTitle;
    }

    public void setLogTitle(String logTitle) {
        LogTitle = logTitle;
    }

    public String getLogContent() {
        return LogContent;
    }

    public void setLogContent(String logContent) {
        LogContent = logContent;
    }

    public String getPriase() {
        return Priase;
    }

    public void setPriase(String priase) {
        Priase = priase;
    }

    public String getImgCollectionFirst() {
        return imgCollectionFirst;
    }

    public void setImgCollectionFirst(String imgCollectionFirst) {
        this.imgCollectionFirst = imgCollectionFirst;
    }

    public String getImgCollection() {
        return imgCollection;
    }

    public void setImgCollection(String imgCollection) {
        this.imgCollection = imgCollection;
    }

    public String getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(String isClicked) {
        this.isClicked = isClicked;
    }

    public String getUsernameSet() {
        return usernameSet;
    }

    public void setUsernameSet(String usernameSet) {
        this.usernameSet = usernameSet;
    }

    @Override
    public String toString() {
        return "LossWeightStoryModel{" +
                "UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", AcBanner='" + AcBanner + '\'' +
                ", LossLogId='" + LossLogId + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", LogTitle='" + LogTitle + '\'' +
                ", LogContent='" + LogContent + '\'' +
                ", Priase='" + Priase + '\'' +
                ", imgCollectionFirst='" + imgCollectionFirst + '\'' +
                ", imgCollection='" + imgCollection + '\'' +
                ", isClicked='" + isClicked + '\'' +
                ", usernameSet='" + usernameSet + '\'' +
                '}';
    }
}
