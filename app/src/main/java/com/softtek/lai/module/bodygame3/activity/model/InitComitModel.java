package com.softtek.lai.module.bodygame3.activity.model;

import retrofit.mime.TypedFile;

/**
 * Created by lareina.qiao on 2016/11/22.
 */

public class InitComitModel {
    private Long accountId;
    private String classId;
    private double pysical;
    private double ChuWeight;
    private double fat;
    private double circum;
    private double waistline;
    private double upArmGirth;
    private double upLegGirth;
    private double doLegGirth;
    private TypedFile image;

    @Override
    public String toString() {
        return "InitComitModel{" +
                "accountId=" + accountId +
                ", classId='" + classId + '\'' +
                ", pysical=" + pysical +
                ", ChuWeight=" + ChuWeight +
                ", fat=" + fat +
                ", circum=" + circum +
                ", waistline=" + waistline +
                ", upArmGirth=" + upArmGirth +
                ", upLegGirth=" + upLegGirth +
                ", doLegGirth=" + doLegGirth +
                ", image=" + image +
                '}';
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public double getPysical() {
        return pysical;
    }

    public void setPysical(double pysical) {
        this.pysical = pysical;
    }

    public double getChuWeight() {
        return ChuWeight;
    }

    public void setChuWeight(double chuWeight) {
        ChuWeight = chuWeight;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCircum() {
        return circum;
    }

    public void setCircum(double circum) {
        this.circum = circum;
    }

    public double getWaistline() {
        return waistline;
    }

    public void setWaistline(double waistline) {
        this.waistline = waistline;
    }

    public double getUpArmGirth() {
        return upArmGirth;
    }

    public void setUpArmGirth(double upArmGirth) {
        this.upArmGirth = upArmGirth;
    }

    public double getUpLegGirth() {
        return upLegGirth;
    }

    public void setUpLegGirth(double upLegGirth) {
        this.upLegGirth = upLegGirth;
    }

    public double getDoLegGirth() {
        return doLegGirth;
    }

    public void setDoLegGirth(double doLegGirth) {
        this.doLegGirth = doLegGirth;
    }

    public TypedFile getImage() {
        return image;
    }

    public void setImage(TypedFile image) {
        this.image = image;
    }
}
