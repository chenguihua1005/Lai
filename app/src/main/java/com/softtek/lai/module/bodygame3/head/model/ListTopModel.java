package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by 87356 on 2016/12/3.
 */
public class ListTopModel {
    private String UserId;//用户id
    private String UserName;//用户名
    private String UserIconUrl;//用户头像
    private String Ranking;//排名
    private String LossPer;//减比
    private String Loss;//减重、脂

    @Override
    public String toString() {
        return "ListTopModel{" +
                "UserId='" + UserId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", UserIconUrl='" + UserIconUrl + '\'' +
                ", Ranking='" + Ranking + '\'' +
                ", LossPer='" + LossPer + '\'' +
                ", Loss='" + Loss + '\'' +
                '}';
    }

    public String getLoss() {
        return Loss;
    }

    public void setLoss(String loss) {
        Loss = loss;
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
}
