package com.softtek.lai.module.jingdu.model;

/**
 * Created by zcy on 2016/4/6.
 */
public class Table1Model {
    private String AccountId;
    private String BeforeWight;
    private String AfterWeight;
    private String LoseWeight;
    private String UserName;

    public Table1Model(String accountId, String beforeWight, String afterWeight, String loseWeight, String userName) {
        AccountId = accountId;
        BeforeWight = beforeWight;
        AfterWeight = afterWeight;
        LoseWeight = loseWeight;
        UserName = userName;
    }

    @Override
    public String toString() {
        return "Table1Model{" +
                "AccountId='" + AccountId + '\'' +
                ", BeforeWight='" + BeforeWight + '\'' +
                ", AfterWeight='" + AfterWeight + '\'' +
                ", LoseWeight='" + LoseWeight + '\'' +
                ", UserName='" + UserName + '\'' +
                '}';
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getBeforeWight() {
        return BeforeWight;
    }

    public void setBeforeWight(String beforeWight) {
        BeforeWight = beforeWight;
    }

    public String getAfterWeight() {
        return AfterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        AfterWeight = afterWeight;
    }

    public String getLoseWeight() {
        return LoseWeight;
    }

    public void setLoseWeight(String loseWeight) {
        LoseWeight = loseWeight;
    }
}
