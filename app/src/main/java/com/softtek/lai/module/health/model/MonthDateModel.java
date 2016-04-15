package com.softtek.lai.module.health.model;

/**
 * Created by John on 2016/4/13.
 */
public class MonthDateModel {
    private String AccountId;
    private String circum;
    private String createDate;
    private String doLegGirth;
    private String fat;
    private String hiplie;
    private String pysical;
    private String upArmGirth;
    private String upLegGirth;
    private String waistline;
    private String weight;

    @Override
    public String toString() {
        return "WeekDateModel{" +
                "AccountId='" + AccountId + '\'' +
                ", circum='" + circum + '\'' +
                ", createDate='" + createDate + '\'' +
                ", doLegGirth='" + doLegGirth + '\'' +
                ", fat='" + fat + '\'' +
                ", hiplie='" + hiplie + '\'' +
                ", pysical='" + pysical + '\'' +
                ", upArmGirth='" + upArmGirth + '\'' +
                ", upLegGirth='" + upLegGirth + '\'' +
                ", waistline='" + waistline + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getCircum() {
        return circum;
    }

    public void setCircum(String circum) {
        this.circum = circum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDoLegGirth() {
        return doLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        this.doLegGirth = doLegGirth;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getHiplie() {
        return hiplie;
    }

    public void setHiplie(String hiplie) {
        this.hiplie = hiplie;
    }

    public String getPysical() {
        return pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
    }

    public String getUpArmGirth() {
        return upArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        this.upArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return upLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        this.upLegGirth = upLegGirth;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
