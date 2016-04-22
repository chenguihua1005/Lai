package com.softtek.lai.module.healthrecords.model;

/**
 * Created by zcy on 2016/4/18.
 * 健康录入表单
 */
public class HealthModel {
    private long accountId;
    private String weight;
    private String pysical;
    private String fat;
    private String circum;
    private String hiplie;
    private String waistline;
    private String upArmGirth;
    private String upLegGirth;
    private String doLegGirth;

    public HealthModel() {

    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPysical() {
        return pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCircum() {
        return circum;
    }

    public void setCircum(String circum) {
        this.circum = circum;
    }

    public String getHiplie() {
        return hiplie;
    }

    public void setHiplie(String hiplie) {
        this.hiplie = hiplie;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
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

    public String getDoLegGirth() {
        return doLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        this.doLegGirth = doLegGirth;
    }

    @Override
    public String toString() {
        return "HealthModel{" +
                "weight='" + weight + '\'' +
                ", pysical='" + pysical + '\'' +
                ", fat='" + fat + '\'' +
                ", circum='" + circum + '\'' +
                ", hiplie='" + hiplie + '\'' +
                ", waistline='" + waistline + '\'' +
                ", upArmGirth='" + upArmGirth + '\'' +
                ", upLegGirth='" + upLegGirth + '\'' +
                ", doLegGirth='" + doLegGirth + '\'' +
                '}';
    }
}
