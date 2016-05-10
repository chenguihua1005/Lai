package com.softtek.lai.module.mygrades.model;

/**
 * Created by julie.zhu on 5/3/2016.
 * 3.3.2	勋章详情页
 */
public class HonorModel {
    private String ThreeDays;
    private String SevenDays;
    private String twentyOneDays;
    private String thirtyDays;
    private String OneHundredDays;
    private String TwoHundredyDays;
    private String OneYearDays;
    private String Totals;//总步数，步数勋章用该字段自动判断
    private String DayOrderTotal;//跑团人数
    private String DayOrder;//当日跑团排名
    private String ContryDayOrderTotal;//全国总人数
    private String ContryDayOrder;//当日全国排名
    private String WeekOrderRG;//跑团当周排名
    private String WeekOrder;//当周全国排名

    public HonorModel(String threeDays, String sevenDays, String twentyOneDays, String thirtyDays, String oneHundredDays, String twoHundredyDays, String oneYearDays, String totals, String dayOrderTotal, String dayOrder, String contryDayOrderTotal, String contryDayOrder, String weekOrderRG, String weekOrder) {
        ThreeDays = threeDays;
        SevenDays = sevenDays;
        this.twentyOneDays = twentyOneDays;
        this.thirtyDays = thirtyDays;
        OneHundredDays = oneHundredDays;
        TwoHundredyDays = twoHundredyDays;
        OneYearDays = oneYearDays;
        Totals = totals;
        DayOrderTotal = dayOrderTotal;
        DayOrder = dayOrder;
        ContryDayOrderTotal = contryDayOrderTotal;
        ContryDayOrder = contryDayOrder;
        WeekOrderRG = weekOrderRG;
        WeekOrder = weekOrder;
    }

    @Override
    public String toString() {
        return "HonorModel{" +
                "ThreeDays='" + ThreeDays + '\'' +
                ", SevenDays='" + SevenDays + '\'' +
                ", twentyOneDays='" + twentyOneDays + '\'' +
                ", thirtyDays='" + thirtyDays + '\'' +
                ", OneHundredDays='" + OneHundredDays + '\'' +
                ", TwoHundredyDays='" + TwoHundredyDays + '\'' +
                ", OneYearDays='" + OneYearDays + '\'' +
                ", Totals='" + Totals + '\'' +
                ", DayOrderTotal='" + DayOrderTotal + '\'' +
                ", DayOrder='" + DayOrder + '\'' +
                ", ContryDayOrderTotal='" + ContryDayOrderTotal + '\'' +
                ", ContryDayOrder='" + ContryDayOrder + '\'' +
                ", WeekOrderRG='" + WeekOrderRG + '\'' +
                ", WeekOrder='" + WeekOrder + '\'' +
                '}';
    }

    public String getThreeDays() {
        return ThreeDays;
    }

    public void setThreeDays(String threeDays) {
        ThreeDays = threeDays;
    }

    public String getSevenDays() {
        return SevenDays;
    }

    public void setSevenDays(String sevenDays) {
        SevenDays = sevenDays;
    }

    public String getTwentyOneDays() {
        return twentyOneDays;
    }

    public void setTwentyOneDays(String twentyOneDays) {
        this.twentyOneDays = twentyOneDays;
    }

    public String getThirtyDays() {
        return thirtyDays;
    }

    public void setThirtyDays(String thirtyDays) {
        this.thirtyDays = thirtyDays;
    }

    public String getOneHundredDays() {
        return OneHundredDays;
    }

    public void setOneHundredDays(String oneHundredDays) {
        OneHundredDays = oneHundredDays;
    }

    public String getTwoHundredyDays() {
        return TwoHundredyDays;
    }

    public void setTwoHundredyDays(String twoHundredyDays) {
        TwoHundredyDays = twoHundredyDays;
    }

    public String getOneYearDays() {
        return OneYearDays;
    }

    public void setOneYearDays(String oneYearDays) {
        OneYearDays = oneYearDays;
    }

    public String getTotals() {
        return Totals;
    }

    public void setTotals(String totals) {
        Totals = totals;
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
