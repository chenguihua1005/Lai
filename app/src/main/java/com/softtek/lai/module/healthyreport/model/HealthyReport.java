package com.softtek.lai.module.healthyreport.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/14/2017.
 */

public class HealthyReport {


    /**
     * username : 铅笔
     * measureTime : 4月12日 17:15
     * bodyType : 7
     * bodyTypeTitle : 清瘦型
     * bodyTypeDesc : 清瘦型:<br/>你的BMI和体脂率均偏低，属于清瘦型身材。请适当增加能量摄入和食物多样化，多补充蛋白质，同时坚持每日适量运动。
     */
    private String accountId;//被测量人id
    private String username;
    private String measureTime;
    private int bodyType;
    private String bodyTypeTitle;
    private String bodyTypeDesc;
    List<HealthyItem> itemList;

    public List<BodyDimensions> getBodyDimensions() {
        return bodyDimensions;
    }

    public void setBodyDimensions(List<BodyDimensions> bodyDimensions) {
        this.bodyDimensions = bodyDimensions;
    }

    List<BodyDimensions> bodyDimensions;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<HealthyItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<HealthyItem> itemList) {
        this.itemList = itemList;
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
}
