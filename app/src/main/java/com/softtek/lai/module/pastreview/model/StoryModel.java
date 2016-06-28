package com.softtek.lai.module.pastreview.model;

import java.io.Serializable;

/**
 * Created by John on 2016/4/6.
 * 减重日志模型
 */
public class StoryModel implements Serializable{

    private String LossLogId;
    private String AfterWeight;
    private String CreateDate;
    private String LogTitle;
    private String LogContent;
    private String imgCollectionFirst;
    private String imgCollection;
    private String usernameSet;
    private String Priase;
    private String isClicked;

    public String getUsernameSet() {
        return usernameSet;
    }

    public void setUsernameSet(String usernameSet) {
        this.usernameSet = usernameSet;
    }

    public String getAfterWeight() {
        return AfterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        AfterWeight = afterWeight;
    }

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

}
