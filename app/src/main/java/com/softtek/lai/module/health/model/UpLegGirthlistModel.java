package com.softtek.lai.module.health.model;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class UpLegGirthlistModel {
    private String AccountId;
    private String createDate;
    private String UpLegGirth;

    @Override
    public String toString() {
        return "UpLegGirthlistModel{" +
                "AccountId='" + AccountId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", UpLegGirth='" + UpLegGirth + '\'' +
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

    public String getUpLegGirth() {
        return UpLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        UpLegGirth = upLegGirth;
    }
}
