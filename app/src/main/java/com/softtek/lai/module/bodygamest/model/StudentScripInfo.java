package com.softtek.lai.module.bodygamest.model;

/**
 * Created by jarvis.liu on 3/31/2016.
 */
public class StudentScripInfo {
    private String Ranking;
    private String UserName;
    private String BeforeWeight;
    private String AfterWeight;
    private String loss;
    private String Change;

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBeforeWeight() {
        return BeforeWeight;
    }

    public void setBeforeWeight(String beforeWeight) {
        BeforeWeight = beforeWeight;
    }

    public String getAfterWeight() {
        return AfterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        AfterWeight = afterWeight;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getChange() {
        return Change;
    }

    public void setChange(String change) {
        Change = change;
    }

    @Override
    public String toString() {
        return "StudentScripInfo{" +
                "Ranking='" + Ranking + '\'' +
                ", UserName='" + UserName + '\'' +
                ", BeforeWeight='" + BeforeWeight + '\'' +
                ", AfterWeight='" + AfterWeight + '\'' +
                ", loss='" + loss + '\'' +
                ", Change='" + Change + '\'' +
                '}';
    }
}
