package com.softtek.lai.module.sport2.model;

import java.util.List;

/**
 * Created by lareina.qiao on 5/27/2016.
 */
public class ScoreModel {
    private String RgTime;
    private String TotalStep;
    private String TodayKaluli;
    private String DayOrder;
    private String ContryDayOrder;

    @Override
    public String toString() {
        return "ScoreModel{" +
                "RgTime='" + RgTime + '\'' +
                ", TotalStep='" + TotalStep + '\'' +
                ", TodayKaluli='" + TodayKaluli + '\'' +
                ", DayOrder='" + DayOrder + '\'' +
                ", ContryDayOrder='" + ContryDayOrder + '\'' +
                '}';
    }

    public String getRgTime() {
        return RgTime;
    }

    public void setRgTime(String rgTime) {
        RgTime = rgTime;
    }

    public String getTotalStep() {
        return TotalStep;
    }

    public void setTotalStep(String totalStep) {
        TotalStep = totalStep;
    }

    public String getTodayKaluli() {
        return TodayKaluli;
    }

    public void setTodayKaluli(String todayKaluli) {
        TodayKaluli = todayKaluli;
    }

    public String getDayOrder() {
        return DayOrder;
    }

    public void setDayOrder(String dayOrder) {
        DayOrder = dayOrder;
    }

    public String getContryDayOrder() {
        return ContryDayOrder;
    }

    public void setContryDayOrder(String contryDayOrder) {
        ContryDayOrder = contryDayOrder;
    }
}
