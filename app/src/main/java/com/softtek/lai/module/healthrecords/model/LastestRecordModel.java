package com.softtek.lai.module.healthrecords.model;

/**
 * Created by julie.zhu on 4/19/2016.
 */
public class LastestRecordModel {
    private String AccountId;
    private String Weight;
    private String Pysical;
    private String Fat;
    private String Circum;
    private String Hiplie;
    private String Waistline;
    private String UpArmGirth;
    private String UpLegGirth;
    private String DoLegGirth;

    @Override
    public String toString() {
        return "LastestRecordModel{" +
                "AccountId='" + AccountId + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Pysical='" + Pysical + '\'' +
                ", Fat='" + Fat + '\'' +
                ", Circum='" + Circum + '\'' +
                ", Hiplie='" + Hiplie + '\'' +
                ", Waistline='" + Waistline + '\'' +
                ", UpArmGirth='" + UpArmGirth + '\'' +
                ", UpLegGirth='" + UpLegGirth + '\'' +
                ", DoLegGirth='" + DoLegGirth + '\'' +
                '}';
    }

    public LastestRecordModel(String accountId, String weight, String pysical, String fat, String circum, String hiplie, String waistline, String upArmGirth, String upLegGirth, String doLegGirth) {
        AccountId = accountId;
        Weight = weight;
        Pysical = pysical;
        Fat = fat;
        Circum = circum;
        Hiplie = hiplie;
        Waistline = waistline;
        UpArmGirth = upArmGirth;
        UpLegGirth = upLegGirth;
        DoLegGirth = doLegGirth;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getPysical() {
        return Pysical;
    }

    public void setPysical(String pysical) {
        Pysical = pysical;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getCircum() {
        return Circum;
    }

    public void setCircum(String circum) {
        Circum = circum;
    }

    public String getHiplie() {
        return Hiplie;
    }

    public void setHiplie(String hiplie) {
        Hiplie = hiplie;
    }

    public String getWaistline() {
        return Waistline;
    }

    public void setWaistline(String waistline) {
        Waistline = waistline;
    }

    public String getUpArmGirth() {
        return UpArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        UpArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return UpLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        UpLegGirth = upLegGirth;
    }

    public String getDoLegGirth() {
        return DoLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        DoLegGirth = doLegGirth;
    }
}
