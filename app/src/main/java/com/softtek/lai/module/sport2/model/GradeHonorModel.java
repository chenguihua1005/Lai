package com.softtek.lai.module.sport2.model;

import java.util.List;

/**
 * Created by julie.zhu on 5/11/2016.
 * 3.3.2	成绩勋章信息
 */
public class GradeHonorModel {

    private String TotalStep;//			--历史总步数
    private String TotalHonor;
    private List<LaiHonorModel> LaiHonor;  //--最近3个荣誉榜
    private String DayOrderTotal;//跑团人数
    private String DayOrder;//当日跑团排名
    private String ContryDayOrderTotal;//全国总人数
    private String ContryDayOrder;//当日全国排名
    private String WeekOrderRG;//跑团当周排名
    private String WeekOrder;//当周全国排名

    public GradeHonorModel(String totalStep, String totalHonor, List<LaiHonorModel> LaiHonor, String dayOrderTotal, String dayOrder, String contryDayOrderTotal, String contryDayOrder, String weekOrderRG, String weekOrder) {
        TotalStep = totalStep;
        TotalHonor = totalHonor;
        this.LaiHonor = LaiHonor;
        DayOrderTotal = dayOrderTotal;
        DayOrder = dayOrder;
        ContryDayOrderTotal = contryDayOrderTotal;
        ContryDayOrder = contryDayOrder;
        WeekOrderRG = weekOrderRG;
        WeekOrder = weekOrder;
    }

    @Override
    public String toString() {
        return "GradeHonorModel{" +
                "TotalStep='" + TotalStep + '\'' +
                ", TotalHonor='" + TotalHonor + '\'' +
                ", LaiHonor=" + LaiHonor +
                ", DayOrderTotal='" + DayOrderTotal + '\'' +
                ", DayOrder='" + DayOrder + '\'' +
                ", ContryDayOrderTotal='" + ContryDayOrderTotal + '\'' +
                ", ContryDayOrder='" + ContryDayOrder + '\'' +
                ", WeekOrderRG='" + WeekOrderRG + '\'' +
                ", WeekOrder='" + WeekOrder + '\'' +
                '}';
    }

    public String getTotalStep() {
        return TotalStep;
    }

    public void setTotalStep(String totalStep) {
        TotalStep = totalStep;
    }

    public String getTotalHonor() {
        return TotalHonor;
    }

    public void setTotalHonor(String totalHonor) {
        TotalHonor = totalHonor;
    }

    public List<LaiHonorModel> getLaiHonor() {
        return LaiHonor;
    }

    public void setLaiHonor(List<LaiHonorModel> LaiHonor) {
        this.LaiHonor = LaiHonor;
    }

    public String getDayOrderTotal() {
        return DayOrderTotal;
    }

    public void setDayOrderTotal(String dayOrderTotal) {
        DayOrderTotal = dayOrderTotal;
    }

    public String getDayOrder() {
        return DayOrder;
    }

    public void setDayOrder(String dayOrder) {
        DayOrder = dayOrder;
    }

    public String getContryDayOrderTotal() {
        return ContryDayOrderTotal;
    }

    public void setContryDayOrderTotal(String contryDayOrderTotal) {
        ContryDayOrderTotal = contryDayOrderTotal;
    }

    public String getContryDayOrder() {
        return ContryDayOrder;
    }

    public void setContryDayOrder(String contryDayOrder) {
        ContryDayOrder = contryDayOrder;
    }

    public String getWeekOrderRG() {
        return WeekOrderRG;
    }

    public void setWeekOrderRG(String weekOrderRG) {
        WeekOrderRG = weekOrderRG;
    }

    public String getWeekOrder() {
        return WeekOrder;
    }

    public void setWeekOrder(String weekOrder) {
        WeekOrder = weekOrder;
    }
}
