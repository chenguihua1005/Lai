/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.group.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class SportMainModel implements Serializable {

    private String TodayStepCnt;       //今日步数
    private String TodayKaluliCnt;     //今日卡路里
    private String TodayStepOdr;       //今日排名
    private String DonatenNum;         //捐赠贡献值
    private String MedalCnt;           //勋章数
    private String RGName;           //跑团名
    private List<RecentlyActiviteModel> RecentlyActivite;
    private PraiseChallengeModel PraiseChallenge;

    @Override
    public String toString() {
        return "SportMainModel{" +
                "TodayStepCnt='" + TodayStepCnt + '\'' +
                ", TodayKaluliCnt='" + TodayKaluliCnt + '\'' +
                ", TodayStepOdr='" + TodayStepOdr + '\'' +
                ", DonatenNum='" + DonatenNum + '\'' +
                ", MedalCnt='" + MedalCnt + '\'' +
                ", RGName='" + RGName + '\'' +
                ", RecentlyActivite=" + RecentlyActivite +
                ", PraiseChallenge=" + PraiseChallenge +
                '}';
    }

    public String getTodayStepCnt() {
        return TodayStepCnt;
    }

    public void setTodayStepCnt(String todayStepCnt) {
        TodayStepCnt = todayStepCnt;
    }

    public String getTodayKaluliCnt() {
        return TodayKaluliCnt;
    }

    public void setTodayKaluliCnt(String todayKaluliCnt) {
        TodayKaluliCnt = todayKaluliCnt;
    }

    public String getTodayStepOdr() {
        return TodayStepOdr;
    }

    public void setTodayStepOdr(String todayStepOdr) {
        TodayStepOdr = todayStepOdr;
    }

    public String getDonatenNum() {
        return DonatenNum;
    }

    public void setDonatenNum(String donatenNum) {
        DonatenNum = donatenNum;
    }

    public String getMedalCnt() {
        return MedalCnt;
    }

    public void setMedalCnt(String medalCnt) {
        MedalCnt = medalCnt;
    }

    public String getRGName() {
        return RGName;
    }

    public void setRGName(String RGName) {
        this.RGName = RGName;
    }

    public List<RecentlyActiviteModel> getRecentlyActivite() {
        return RecentlyActivite;
    }

    public void setRecentlyActivite(List<RecentlyActiviteModel> recentlyActivite) {
        RecentlyActivite = recentlyActivite;
    }

    public PraiseChallengeModel getPraiseChallenge() {
        return PraiseChallenge;
    }

    public void setPraiseChallenge(PraiseChallengeModel praiseChallenge) {
        PraiseChallenge = praiseChallenge;
    }
}
