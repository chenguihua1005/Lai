package com.softtek.lai.module.studetail.model;

import java.io.Serializable;

/**
 * Created by John on 2016/4/6.
 * 减重日志模型
 */
public class LossWeightLogModel implements Serializable{

    private String UserName;
    private String Photo;
    private String AcBanner;
    private String LossLogId;
    private String CreateDate;
    private String LogTitle;
    private String LogContent;
    private String imgCollectionFirst;
    private String imgCollection;
    private String Priase;
    private String isClicked;

    public String getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(String isClicked) {
        this.isClicked = isClicked;
    }

    public String getPriase() {
        return Priase;
    }

    public void setPriase(String priase) {
        Priase = priase;
    }

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

    @Override
    public String toString() {
        return "LossWeightLogModel{" +
                "UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", AcBanner='" + AcBanner + '\'' +
                ", LossLogId='" + LossLogId + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", LogTitle='" + LogTitle + '\'' +
                ", LogContent='" + LogContent + '\'' +
                ", imgCollectionFirst='" + imgCollectionFirst + '\'' +
                ", imgCollection='" + imgCollection + '\'' +
                ", Priase='" + Priase + '\'' +
                ", isClicked='" + isClicked + '\'' +
                '}';
    }
}
