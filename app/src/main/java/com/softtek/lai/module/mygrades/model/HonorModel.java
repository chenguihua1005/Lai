package com.softtek.lai.module.mygrades.model;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public class HonorModel {
    private String ThreeDays;
    private String SevenDays;
    private String twentyOneDays;
    private String thirtyDays;
    private String OneHundredDays;
    private String TwoHundredyDays;
    private String OneYearDays;
    private String Totals;

    public HonorModel(String threeDays, String sevenDays, String twentyOneDays, String thirtyDays, String oneHundredDays, String twoHundredyDays, String oneYearDays, String totals) {
        ThreeDays = threeDays;
        SevenDays = sevenDays;
        this.twentyOneDays = twentyOneDays;
        this.thirtyDays = thirtyDays;
        OneHundredDays = oneHundredDays;
        TwoHundredyDays = twoHundredyDays;
        OneYearDays = oneYearDays;
        Totals = totals;
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
}
