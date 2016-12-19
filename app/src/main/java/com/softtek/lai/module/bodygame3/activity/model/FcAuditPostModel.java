package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by lareina.qiao on 12/16/2016.
 */

public class FcAuditPostModel {
private String ACMId;
private String AccountId;
private String ReviewerId;
private String Weight;
private String Pysical;
private String Fat;
private String Circum;
private String Waistline;
private String Hiplie;
private String UpArmGirth;
private String UpLegGirth;
private String DoLegGirth;

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
                '}';
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
