package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class LatestRecordModel {
    private long accountId;
    private String username;
    private String measureTime;//测量时间
    private int bodyType;//体型编号
    private String bodyTypeTitle;
    private String bodyTypeDesc;
    private List<HealthyItem> itemList;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    public int getBodyType() {
        return bodyType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public String getBodyTypeTitle() {
        return bodyTypeTitle;
    }

    public void setBodyTypeTitle(String bodyTypeTitle) {
        this.bodyTypeTitle = bodyTypeTitle;
    }

    public String getBodyTypeDesc() {
        return bodyTypeDesc;
    }

    public void setBodyTypeDesc(String bodyTypeDesc) {
        this.bodyTypeDesc = bodyTypeDesc;
    }

    public List<HealthyItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<HealthyItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "LatestRecordModel{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", measureTime='" + measureTime + '\'' +
                ", bodyType=" + bodyType +
                ", bodyTypeTitle='" + bodyTypeTitle + '\'' +
                ", bodyTypeDesc='" + bodyTypeDesc + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
