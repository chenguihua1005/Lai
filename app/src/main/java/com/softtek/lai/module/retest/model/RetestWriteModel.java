package com.softtek.lai.module.retest.model;

import java.io.Serializable;

/**
 * Created by lareina.qiao on 3/26/2016.
 */
public class RetestWriteModel implements Serializable{
    private String accountId;
    private String weight;
    private String pysical;
    private String fat;
    private String circum;
    private String waistline;
    private String hiplie;
    private String upArmGirth;
    private String upLegGirth;
    private String doLegGirth;
    private String image;
    private String classId;
    private String InitWeight;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getWeight() {
        return weight==null?"":weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPysical() {
        return pysical==null?"":pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
    }

    public String getFat() {
        return fat==null?"":fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCircum() {
        return circum==null?"":circum;
    }

    public void setCircum(String circum) {
        this.circum = circum;
    }

    public String getWaistline() {
        return waistline==null?"":waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHiplie() {
        return hiplie==null?"":hiplie;
    }

    public void setHiplie(String hiplie) {
        this.hiplie = hiplie;
    }

    public String getUpArmGirth() {
        return upArmGirth==null?"":upArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        this.upArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return upLegGirth==null?"":upLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        this.upLegGirth = upLegGirth;
    }

    public String getDoLegGirth() {
        return doLegGirth==null?"":doLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        this.doLegGirth = doLegGirth;
    }

    public String getImage() {
        return image==null?"":image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getInitWeight() {
        return InitWeight;
    }

    public void setInitWeight(String initWeight) {
        InitWeight = initWeight;
    }
}
