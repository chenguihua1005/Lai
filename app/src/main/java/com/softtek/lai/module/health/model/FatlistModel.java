package com.softtek.lai.module.health.model;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class FatlistModel {
    private String AccountId;
    private String createDate;
    private String fat;

    @Override
    public String toString() {
        return "FatlistModel{" +
                "AccountId='" + AccountId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", fat='" + fat + '\'' +
                '}';
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }
}
