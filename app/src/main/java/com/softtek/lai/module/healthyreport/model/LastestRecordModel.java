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

    private String Bmi; //BMI
    private String FatFreeMass;//去脂体重
//    private String ViscusFatIndex;     //内脏脂肪指数
    private String BodyWaterRate;//身体水分率
    private String BodyWater;//身体水分
    private String MuscleMass;//肌肉量
    private String BoneMass;//骨量
    private String BasalMetabolism;//基础代谢
    private String PhysicalAge;//身体年龄

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
                ", Bmi='" + Bmi + '\'' +
                ", FatFreeMass='" + FatFreeMass + '\'' +
                ", BodyWaterRate='" + BodyWaterRate + '\'' +
                ", BodyWater='" + BodyWater + '\'' +
                ", MuscleMass='" + MuscleMass + '\'' +
                ", BoneMass='" + BoneMass + '\'' +
                ", BasalMetabolism='" + BasalMetabolism + '\'' +
                ", PhysicalAge='" + PhysicalAge + '\'' +
                '}';
    }

    public String getBmi() {
        return Bmi;
    }

    public void setBmi(String bmi) {
        Bmi = bmi;
    }

    public String getFatFreeMass() {
        return FatFreeMass;
    }

    public void setFatFreeMass(String fatFreeMass) {
        FatFreeMass = fatFreeMass;
    }

//    public String getViscusFatIndex() {
//        return ViscusFatIndex;
//    }
//
//    public void setViscusFatIndex(String viscusFatIndex) {
//        ViscusFatIndex = viscusFatIndex;
//    }

    public String getBodyWaterRate() {
        return BodyWaterRate;
    }

    public void setBodyWaterRate(String bodyWaterRate) {
        BodyWaterRate = bodyWaterRate;
    }

    public String getBodyWater() {
        return BodyWater;
    }

    public void setBodyWater(String bodyWater) {
        BodyWater = bodyWater;
    }

    public String getMuscleMass() {
        return MuscleMass;
    }

    public void setMuscleMass(String muscleMass) {
        MuscleMass = muscleMass;
    }

    public String getBoneMass() {
        return BoneMass;
    }

    public void setBoneMass(String boneMass) {
        BoneMass = boneMass;
    }

    public String getBasalMetabolism() {
        return BasalMetabolism;
    }

    public void setBasalMetabolism(String basalMetabolism) {
        BasalMetabolism = basalMetabolism;
    }

    public String getPhysicalAge() {
        return PhysicalAge;
    }

    public void setPhysicalAge(String physicalAge) {
        PhysicalAge = physicalAge;
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
