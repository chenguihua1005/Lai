package com.softtek.lai.module.health.model;

/**
 * Created by lareina.qiao on 4/22/2016.
 */
public class weightlistModel {
    private String AccountId;
    private String createDate;
    private String weight;

    @Override
    public String toString() {
        return "weightlistModel{" +
                "AccountId='" + AccountId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", weight='" + weight + '\'' +
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
