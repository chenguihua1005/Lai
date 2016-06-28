package com.softtek.lai.module.pastreview.model;

/**
 * Created by jerry.guan on 6/28/2016.
 */
public class PastBaseData {

    private String AccountId;
    private String IsTest;
    private String totalLoss;
    private String beforeWeight;
    private String afterWeight;
    private String beforeImage;
    private String afterImage;

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getIsTest() {
        return IsTest;
    }

    public void setIsTest(String isTest) {
        IsTest = isTest;
    }

    public String getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(String totalLoss) {
        this.totalLoss = totalLoss;
    }

    public String getBeforeWeight() {
        return beforeWeight;
    }

    public void setBeforeWeight(String beforeWeight) {
        this.beforeWeight = beforeWeight;
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

    public String getAfterImage() {
        return afterImage;
    }

    public void setAfterImage(String afterImage) {
        this.afterImage = afterImage;
    }
}
