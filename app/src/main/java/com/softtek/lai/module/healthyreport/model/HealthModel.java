package com.softtek.lai.module.healthyreport.model;

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

    private String Bmi; //BMI
    private String FatFreeMass;//去脂体重
//    private String ViscusFatIndex;     //内脏脂肪指数
    private String BodyWaterRate;//身体水分率
    private String BodyWater;//身体水分
    private String MuscleMass;//肌肉量
    private String BoneMass;//骨量
    private String BasalMetabolism;//基础代谢
    private String PhysicalAge;//身体年龄

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
                "accountId=" + accountId +
                ", weight='" + weight + '\'' +
                ", pysical='" + pysical + '\'' +
                ", fat='" + fat + '\'' +
                ", circum='" + circum + '\'' +
                ", hiplie='" + hiplie + '\'' +
                ", waistline='" + waistline + '\'' +
                ", upArmGirth='" + upArmGirth + '\'' +
                ", upLegGirth='" + upLegGirth + '\'' +
                ", doLegGirth='" + doLegGirth + '\'' +
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
}
