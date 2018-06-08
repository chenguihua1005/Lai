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
public class MineResultModel implements Serializable {

    private String TodayStepCnt;       //今日步数
    private String TodayKaluliCnt;     //今日卡路里
    private String TodayStepOdr;       //今日排名
    private String MedalCnt;           //勋章数

    @Override
    public String toString() {
        return "MineResultModel{" +
                "TodayStepCnt='" + TodayStepCnt + '\'' +
                ", TodayKaluliCnt='" + TodayKaluliCnt + '\'' +
                ", TodayStepOdr='" + TodayStepOdr + '\'' +
                ", MedalCnt='" + MedalCnt + '\'' +
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

    public String getMedalCnt() {
        return MedalCnt;
    }

    public void setMedalCnt(String medalCnt) {
        MedalCnt = medalCnt;
    }
}
