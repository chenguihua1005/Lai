package com.softtek.lai.module.bodygame3.head.model;

import java.io.Serializable;

/**
 * Created by jessica.zhang on 6/16/2017.
 */

public class ClassMemberModel implements Serializable {
    private String UserId;
    private String UserName;
    private String UserIconUrl;
    private String Ranking;
    private String LossPer;//减脂比
    private String Loss;//减重斤数
    private String CGName;//小组名
    private String InitWeight;//初始体重
    private int ClassRole;// 班级角色
    private String ClassRoleName;//班级角色名称
    private int Gender;

    public void setClassRole(int classRole) {
        ClassRole = classRole;
    }

    public String getClassRoleName() {
        return ClassRoleName;
    }

    public void setClassRoleName(String classRoleName) {
        ClassRoleName = classRoleName;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserIconUrl() {
        return UserIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        UserIconUrl = userIconUrl;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }

    public String getLossPer() {
        return LossPer;
    }

    public void setLossPer(String lossPer) {
        LossPer = lossPer;
    }

    public String getLoss() {
        return Loss;
    }

    public void setLoss(String loss) {
        Loss = loss;
    }

    public String getCGName() {
        return CGName;
    }

    public void setCGName(String CGName) {
        this.CGName = CGName;
    }

    public String getInitWeight() {
        return InitWeight;
    }

    public void setInitWeight(String initWeight) {
        InitWeight = initWeight;
    }


    @Override
    public String toString() {
        return "ClassMemberModel{" +
                "UserId='" + UserId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", UserIconUrl='" + UserIconUrl + '\'' +
                ", Ranking='" + Ranking + '\'' +
                ", LossPer='" + LossPer + '\'' +
                ", Loss='" + Loss + '\'' +
                ", CGName='" + CGName + '\'' +
                ", InitWeight='" + InitWeight + '\'' +
                ", ClassRole=" + ClassRole +
                ", ClassRoleName='" + ClassRoleName + '\'' +
                ", Gender=" + Gender +
                '}';
    }
}
