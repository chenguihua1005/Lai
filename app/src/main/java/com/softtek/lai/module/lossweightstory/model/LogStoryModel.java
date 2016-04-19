package com.softtek.lai.module.lossweightstory.model;

import java.io.Serializable;

/**
 * Created by jerry.guan on 4/16/2016.
 */
public class LogStoryModel implements Serializable{

    private long accountId;
    private String logTitle;
    private String logContent;
    private String storyPeople;
    private String afterWeight;
    private String photoes;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getStoryPeople() {
        return storyPeople;
    }

    public void setStoryPeople(String storyPeople) {
        this.storyPeople = storyPeople;
    }

    public String getAfterWeight() {
        return afterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        this.afterWeight = afterWeight;
    }

    public String getPhotoes() {
        return photoes;
    }

    public void setPhotoes(String photoes) {
        this.photoes = photoes;
    }
}
