package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/12/2016.
 */
public class LossStoryModel {
    private String AccountId;
    private String AfterWeight;
    private String Collection;
    private String CreateDate;
    private String LogContent;
    private String LogTitle;
    private String LossLogId;
    private String LossType;
    private String Priase;
    private String Share;
    private String StoryPeople;

    public LossStoryModel(String accountId, String afterWeight, String collection, String createDate, String logContent, String logTitle, String lossLogId, String lossType, String priase, String share, String storyPeople) {
        AccountId = accountId;
        AfterWeight = afterWeight;
        Collection = collection;
        CreateDate = createDate;
        LogContent = logContent;
        LogTitle = logTitle;
        LossLogId = lossLogId;
        LossType = lossType;
        Priase = priase;
        Share = share;
        StoryPeople = storyPeople;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getAfterWeight() {
        return AfterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        AfterWeight = afterWeight;
    }

    public String getCollection() {
        return Collection;
    }

    public void setCollection(String collection) {
        Collection = collection;
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

    public String getLogTitle() {
        return LogTitle;
    }

    public void setLogTitle(String logTitle) {
        LogTitle = logTitle;
    }

    public String getLossLogId() {
        return LossLogId;
    }

    public void setLossLogId(String lossLogId) {
        LossLogId = lossLogId;
    }

    public String getLossType() {
        return LossType;
    }

    public void setLossType(String lossType) {
        LossType = lossType;
    }

    public String getPriase() {
        return Priase;
    }

    public void setPriase(String priase) {
        Priase = priase;
    }

    public String getShare() {
        return Share;
    }

    public void setShare(String share) {
        Share = share;
    }

    public String getStoryPeople() {
        return StoryPeople;
    }

    public void setStoryPeople(String storyPeople) {
        StoryPeople = storyPeople;
    }
}
