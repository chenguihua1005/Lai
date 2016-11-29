package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class PartnersModel {

    private String AccountId;
    private String Ranking;
    private String StuImg;
    private String StuThImg;
    private String StuName;
    private String StuGender;
    private String GroupName;
    private String GroupId;
    private String Weight;
    private String Loss;

    public PartnersModel(String accountId, String ranking, String stuImg, String stuThImg, String stuName, String stuGender, String groupName, String groupId, String weight, String loss) {
        AccountId = accountId;
        Ranking = ranking;
        StuImg = stuImg;
        StuThImg = stuThImg;
        StuName = stuName;
        StuGender = stuGender;
        GroupName = groupName;
        GroupId = groupId;
        Weight = weight;
        Loss = loss;
    }

    public String getStuThImg() {
        return StuThImg;
    }

    public void setStuThImg(String stuThImg) {
        StuThImg = stuThImg;
    }

    public String getStuGender() {
        return StuGender;
    }

    public void setStuGender(String stuGender) {
        StuGender = stuGender;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }

    public String getStuImg() {
        return StuImg;
    }

    public void setStuImg(String stuImg) {
        StuImg = stuImg;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getLoss() {
        return Loss;
    }

    public void setLoss(String loss) {
        Loss = loss;
    }

    @Override
    public String toString() {
        return "PartnersModel{" +
                "AccountId='" + AccountId + '\'' +
                ", Ranking='" + Ranking + '\'' +
                ", StuImg='" + StuImg + '\'' +
                ", StuName='" + StuName + '\'' +
                ", GroupName='" + GroupName + '\'' +
                ", GroupId='" + GroupId + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Loss='" + Loss + '\'' +
                '}';
    }
}
