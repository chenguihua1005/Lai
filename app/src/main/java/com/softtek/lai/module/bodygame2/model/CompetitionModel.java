package com.softtek.lai.module.bodygame2.model;

/**
 * Created by jerry.guan on 7/11/2016.
 * 体管赛2.0首页大赛赛况
 */
public class CompetitionModel {

    private int AccountId;
    private String ClassName;
    private String GroupName;
    private int LoseWeight;
    private String PCPhoto;
    private String PCUserName;
    private String UserName;
    private int rnum;

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public int getLoseWeight() {
        return LoseWeight;
    }

    public void setLoseWeight(int loseWeight) {
        LoseWeight = loseWeight;
    }

    public String getPCPhoto() {
        return PCPhoto;
    }

    public void setPCPhoto(String PCPhoto) {
        this.PCPhoto = PCPhoto;
    }

    public String getPCUserName() {
        return PCUserName;
    }

    public void setPCUserName(String PCUserName) {
        this.PCUserName = PCUserName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }
}
