package com.softtek.lai.module.healthchart.model;

/**
 * Created by lareina.qiao on 4/19/2016.
 */
public class PysicallistModel {
    private String AccountId;
    private String createDate;
    private String pysical;

    @Override
    public String toString() {
        return "PysicallistModel{" +
                "AccountId='" + AccountId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", pysical='" + pysical + '\'' +
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

    public String getPysical() {
        return pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
    }
}
