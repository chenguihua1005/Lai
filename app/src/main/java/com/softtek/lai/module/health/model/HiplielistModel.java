package com.softtek.lai.module.health.model;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class HiplielistModel {
    private String AccountId;
    private String createDate;
    private String Hiplie;

    @Override
    public String toString() {
        return "HiplielistModel{" +
                "AccountId='" + AccountId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", Hiplie='" + Hiplie + '\'' +
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

    public String getHiplie() {
        return Hiplie;
    }

    public void setHiplie(String hiplie) {
        Hiplie = hiplie;
    }
}
