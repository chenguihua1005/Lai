package com.softtek.lai.module.healthyreport.model;

import com.softtek.lai.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;

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

    public LastestRecordModel() {
    }


    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getWeight() {
        return StringUtils.isEmpty(Weight)?"0":Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getPysical() {
        return StringUtils.isEmpty(Pysical)?"0":Pysical;
    }

    public void setPysical(String pysical) {
        Pysical = pysical;
    }

    public String getFat() {
        return StringUtils.isEmpty(Fat)?"0":Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getCircum() {
        return StringUtils.isEmpty(Circum)?"0":Circum;
    }

    public void setCircum(String circum) {
        Circum = circum;
    }

    public String getHiplie() {
        return StringUtils.isEmpty(Hiplie)?"0":Hiplie;
    }

    public void setHiplie(String hiplie) {
        Hiplie = hiplie;
    }

    public String getWaistline() {
        return StringUtils.isEmpty(Waistline)?"0":Waistline;
    }

    public void setWaistline(String waistline) {
        Waistline = waistline;
    }

    public String getUpArmGirth() {
        return StringUtils.isEmpty(UpArmGirth)?"0":UpArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        UpArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return StringUtils.isEmpty(UpLegGirth)?"0":UpLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        UpLegGirth = upLegGirth;
    }

    public String getDoLegGirth() {
        return StringUtils.isEmpty(DoLegGirth)?"0":DoLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        DoLegGirth = doLegGirth;
    }
}
