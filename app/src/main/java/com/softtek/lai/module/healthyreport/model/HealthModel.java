package com.softtek.lai.module.healthyreport.model;

/**
 * Created by zcy on 2016/4/18.
 * 健康录入表单
 *
 */
public class HealthModel {
    private long accountId;
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
    private String MeasuredTime;//测量时间
    private String ViscusFatIndex;

    public String getViscusFatIndex() {
        return ViscusFatIndex;
    }

    public void setViscusFatIndex(String viscusFatIndex) {
        ViscusFatIndex = viscusFatIndex;
    }

    public String getMeasuredTime() {
        return MeasuredTime;
    }

    public void setMeasuredTime(String measuredTime) {
        MeasuredTime = measuredTime;
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




}
