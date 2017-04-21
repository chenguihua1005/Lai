package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by lareina.qiao on 12/16/2016.
 */

public class FcAuditPostModel {
    private String ACMId;
    private String AccountId;
    private String ReviewerId;
    private String ClassId;
    private String WeekNum;

    private String InitWeight;
    private String Weight;
    private String Pysical;
    private String Fat;
    private String Circum;
    private String Waistline;
    private String Hiplie;
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

    private String FileName;
    private String Thumbnail;

    public String getInitWeight() {
        return InitWeight;
    }

    public void setInitWeight(String initWeight) {
        InitWeight = initWeight;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getWeekNum() {
        return WeekNum;
    }

    public void setWeekNum(String weekNum) {
        WeekNum = weekNum;
    }

    @Override
    public String toString() {
        return "FcAuditPostModel{" +
                "ACMId='" + ACMId + '\'' +
                ", AccountId='" + AccountId + '\'' +
                ", ReviewerId='" + ReviewerId + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Pysical='" + Pysical + '\'' +
                ", Fat='" + Fat + '\'' +
                ", Circum='" + Circum + '\'' +
                ", Waistline='" + Waistline + '\'' +
                ", Hiplie='" + Hiplie + '\'' +
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

    public String getHiplie() {
        return Hiplie;
    }

    public void setHiplie(String hiplie) {
        Hiplie = hiplie;
    }

    public String getACMId() {
        return ACMId;
    }

    public void setACMId(String ACMId) {
        this.ACMId = ACMId;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getReviewerId() {
        return ReviewerId;
    }

    public void setReviewerId(String reviewerId) {
        ReviewerId = reviewerId;
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
