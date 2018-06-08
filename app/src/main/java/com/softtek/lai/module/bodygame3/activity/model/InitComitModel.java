package com.softtek.lai.module.bodygame3.activity.model;

import java.io.File;
import java.io.Serializable;

import retrofit.mime.TypedFile;

/**
 * Created by lareina.qiao on 2016/11/22.
 */

public class InitComitModel implements Serializable{
    private Long accountId;//用户id
    private String classId;//班级id
    private String pysical;//体脂
    private String ChuWeight;//体重
    private String fat;//内脂
    private String circum;//胸围
    private String waistline;//腰围
    private String upArmGirth;//上臂围
    private String upLegGirth;//大腿围
    private String doLegGirth;//小腿围
    private File image;//头像
    private String hipline;//臀围

    @Override
    public String toString() {
        return "InitComitModel{" +
                "accountId=" + accountId +
                ", classId='" + classId + '\'' +
                ", pysical='" + pysical + '\'' +
                ", ChuWeight='" + ChuWeight + '\'' +
                ", fat='" + fat + '\'' +
                ", circum='" + circum + '\'' +
                ", waistline='" + waistline + '\'' +
                ", upArmGirth='" + upArmGirth + '\'' +
                ", upLegGirth='" + upLegGirth + '\'' +
                ", doLegGirth='" + doLegGirth + '\'' +
                ", image=" + image +
                ", hipline='" + hipline + '\'' +
                '}';
    }

    public String getHipline() {
        return hipline;
    }

    public void setHipline(String hipline) {
        this.hipline = hipline;
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

    public String getPysical() {
        return pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
    }

    public String getChuWeight() {
        return ChuWeight;
    }

    public void setChuWeight(String chuWeight) {
        ChuWeight = chuWeight;
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
