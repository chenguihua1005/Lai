package com.softtek.lai.module.bodygame3.activity.model;

import java.io.Serializable;

import retrofit.mime.TypedFile;

/**
 * Created by lareina.qiao on 2016/11/22.
 */

public class InitComitModel implements Serializable{
    private Long accountId;//用户id
    private String classId;//班级id
    private double pysical;//体脂
    private double ChuWeight;//体重
    private double fat;//内脂
    private double circum;//胸围
    private double waistline;//腰围
    private double upArmGirth;//上臂围
    private double upLegGirth;//大腿围
    private double doLegGirth;//小腿围
    private TypedFile image;//头像

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
