package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by curry.zhang on 12/12/2016.
 */

/**
 * 小组排名页面list中单个人的实体类
 */
public class ListGroupRankingModel {
    private String Num;
    private String AccountId;
    private String UserName;
    private String Photo;
    private String LossPer;
    private String InitWeight;
    private String Gender;

    private String Loss;//减重/脂
    private String Target;//减重/脂  增重 增脂

    private int ClassRole;// 班级角色

    private int Status;//是否退赛的标志  状态 1正常 -1退赛

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int classRole) {
        ClassRole = classRole;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public String getLoss() {
        return Loss;
    }

    public void setLoss(String loss) {
        Loss = loss;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getLossPer() {
        return LossPer;
    }

    public void setLossPer(String lossPer) {
        LossPer = lossPer;
    }

    public String getInitWeight() {
        return InitWeight;
    }

    public void setInitWeight(String initWeight) {
        InitWeight = initWeight;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    @Override
    public String toString() {
        return "ListGroupRankingModel{" +
                "Num='" + Num + '\'' +
                ", AccountId='" + AccountId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", LossPer='" + LossPer + '\'' +
                ", InitWeight='" + InitWeight + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Loss='" + Loss + '\'' +
                '}';
    }
}
