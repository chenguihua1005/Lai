package com.softtek.lai.module.retest.model;

/**
 * Created by lareina.qiao on 4/12/2016.
 */
public class AuditPostModel {
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
    private String initWeight;

    @Override
    public String toString() {
        return "AuditPostModel{" +
                "accountId='" + accountId + '\'' +
                ", weight='" + weight + '\'' +
                ", pysical='" + pysical + '\'' +
                ", fat='" + fat + '\'' +
                ", circum='" + circum + '\'' +
                ", waistline='" + waistline + '\'' +
                ", hiplie='" + hiplie + '\'' +
                ", upArmGirth='" + upArmGirth + '\'' +
                ", upLegGirth='" + upLegGirth + '\'' +
                ", doLegGirth='" + doLegGirth + '\'' +
                ", image='" + image + '\'' +
                ", classId='" + classId + '\'' +
                ", initWeight='" + initWeight + '\'' +
                '}';
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
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

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHiplie() {
        return hiplie;
    }

    public void setHiplie(String hiplie) {
        this.hiplie = hiplie;
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

    public String getImage() {
        return image;
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
        return initWeight;
    }

    public void setInitWeight(String initWeight) {
        this.initWeight = initWeight;
    }
}
