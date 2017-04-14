package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by Terry on 2016/11/29.
 */
public class MemberListModel {
    private String acmId;//复测记录ID
    private String userId;
    private String userName;
    private String userIconUrl;
    private String groupId;
    private String groupName;
    private String weight;
    private String initWeight;
    private String weekNum;

    @Override
    public String toString() {
        return "MemberListModel{" +
                "acmId='" + acmId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userIconUrl='" + userIconUrl + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", weight='" + weight + '\'' +
                ", initWeight='" + initWeight + '\'' +
                ", weekNum='" + weekNum + '\'' +
                '}';
    }

    public String getAcmId() {
        return acmId;
    }

    public void setAcmId(String acmId) {
        this.acmId = acmId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getInitWeight() {
        return initWeight;
    }

    public void setInitWeight(String initWeight) {
        this.initWeight = initWeight;
    }

    public String getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum;
    }
}
