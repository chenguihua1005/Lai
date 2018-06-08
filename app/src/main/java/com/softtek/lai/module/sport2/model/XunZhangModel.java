package com.softtek.lai.module.sport2.model;

import java.util.List;

/**
 * Created by lareina.qiao on 5/27/2016.
 */
public class XunZhangModel {
    private String ThreeDays;
    private String SevenDays;
    private String twentyOneDays;
    private String thirtyDays;
    private String OneHundredDays;
    private String TwoHundredyDays;
    private String OneYearDays;
    private String Angle;
    private List<String> Totals;
    private List<String> PK;

    @Override
    public String toString() {
        return "XunZhangModel{" +
                "ThreeDays='" + ThreeDays + '\'' +
                ", SevenDays='" + SevenDays + '\'' +
                ", twentyOneDays='" + twentyOneDays + '\'' +
                ", thirtyDays='" + thirtyDays + '\'' +
                ", OneHundredDays='" + OneHundredDays + '\'' +
                ", TwoHundredyDays='" + TwoHundredyDays + '\'' +
                ", OneYearDays='" + OneYearDays + '\'' +
                ", Angle='" + Angle + '\'' +
                ", Totals=" + Totals +
                ", PK=" + PK +
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

    public String getAngle() {
        return Angle;
    }

    public void setAngle(String angle) {
        Angle = angle;
    }

    public List<String> getTotals() {
        return Totals;
    }

    public void setTotals(List<String> totals) {
        Totals = totals;
    }

    public List<String> getPK() {
        return PK;
    }

    public void setPK(List<String> PK) {
        this.PK = PK;
    }
}
