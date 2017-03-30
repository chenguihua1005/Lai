package com.softtek.lai.module.bodygame3.head.model;

import java.io.Serializable;

/**
 * Created by 87356 on 2016/12/3.
 */
public class ListGroupModel implements Serializable{
    private String GroupId;//小组id
    private String GroupName;//小组名
    private String CoachId;//教练id
    private String CoachName;//教练名
    private String CoachIco;//教练头像
    private String Ranking;//排名
    private String LossPer;//减比
    private String CoachType;//

    private String Loss;//减重、脂

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
