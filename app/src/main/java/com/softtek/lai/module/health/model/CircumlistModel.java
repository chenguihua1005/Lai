package com.softtek.lai.module.health.model;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class CircumlistModel {
    private String AccountId;
    private String createDate;
    private String Circum;

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

    public String getCircum() {
        return Circum;
    }

    public void setCircum(String circum) {
        Circum = circum;
    }

    @Override
    public String toString() {
        return "CircumlistModel{" +
                "AccountId='" + AccountId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", Circum='" + Circum + '\'' +
                '}';
    }
}
