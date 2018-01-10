package com.softtek.lai.module.bodygame3.head.model;

import java.io.Serializable;

/**
 * Created by 87356 on 2016/12/3.
 * <p>
 * "UserId": 81907,
 * "UserName": "毛笔",
 * "UserIconUrl": "201612162143081615068646.png",
 * "Ranking": 1,
 * "LossPer": "10.6",
 * "Loss": "16.4",
 * "CGName": "同桌",
 * "InitWeight": "154.4",
 * "ClassRole": 4,
 * "ClassRoleName": "学员"
 */
public class ListGroupModel implements Serializable {
    private String GroupId;//小组id
    private String GroupName;//小组名
    private String CoachId;//教练id
    private String CoachName;//教练名
    private String CoachIco;//教练头像
    private String Ranking;//排名
    private String LossPer;//减比
    private String CoachType;//
    private String Loss;//减重、脂

    private String UserId;
    private String UserName;
    private String UserIconUrl;
//    private String Ranking;
//    private String LossPer;//减脂比
//    private String Loss;//减重斤数
    private String CGName;//小组名
    private String InitWeight;//初始体重
    private int ClassRole;// 班级角色
    private String ClassRoleName;//班级角色名称
    private int Gender;

    private int Type;//分类  1： 小组   2 ： 班级

    private String Target;// 针对体重Tab页面，增重 or 减重
    private int Status;//是否退赛的标志  状态 1正常 -1退赛

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
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

    public int getClassRole() {
        return ClassRole;
    }

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

    @Override
    public String toString() {
        return "ListGroupModel{" +
                "GroupId='" + GroupId + '\'' +
                ", GroupName='" + GroupName + '\'' +
                ", CoachId='" + CoachId + '\'' +
                ", CoachName='" + CoachName + '\'' +
                ", CoachIco='" + CoachIco + '\'' +
                ", Ranking='" + Ranking + '\'' +
                ", LossPer='" + LossPer + '\'' +
                ", CoachType='" + CoachType + '\'' +
                ", Loss='" + Loss + '\'' +
                '}';
    }

    public String getLoss() {
        return Loss;
    }

    public void setLoss(String loss) {
        Loss = loss;
    }

    public String getCoachType() {
        return CoachType;
    }

    public void setCoachType(String coachType) {
        CoachType = coachType;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getCoachId() {
        return CoachId;
    }

    public void setCoachId(String coachId) {
        CoachId = coachId;
    }

    public String getCoachName() {
        return CoachName;
    }

    public void setCoachName(String coachName) {
        CoachName = coachName;
    }

    public String getCoachIco() {
        return CoachIco;
    }

    public void setCoachIco(String coachIco) {
        CoachIco = coachIco;
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
}
