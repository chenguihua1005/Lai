package com.softtek.lai.module.studetail.model;

/**
 * Created by jerry.guan on 4/5/2016.
 */
public class StudentLinChartInfoModel {

    private long AccountId;
    private int ClassId;
    private String circum;
    private String doLegGirth;
    private String fat;
    private String hiplie;
    private String pysical;
    private String upArmGirth;
    private String upLegGirth;
    private String waistline;
    private String weight;
    private String typeDate;

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public int getClassId() {
        return ClassId;
    }

    public void setClassId(int classId) {
        ClassId = classId;
    }

    public String getCircum() {
        return circum;
    }

    public void setCircum(String circum) {
        this.circum = circum;
    }

    public String getDoLegGirth() {
        return doLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        this.doLegGirth = doLegGirth;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getHiplie() {
        return hiplie;
    }

    public void setHiplie(String hiplie) {
        this.hiplie = hiplie;
    }

    public String getPysical() {
        return pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
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

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTypeDate() {
        return typeDate;
    }

    public void setTypeDate(String typeDate) {
        this.typeDate = typeDate;
    }

    @Override
    public String toString() {
        return "StudentLinChartInfoModel{" +
                "AccountId=" + AccountId +
                ", ClassId=" + ClassId +
                ", circum='" + circum + '\'' +
                ", doLegGirth='" + doLegGirth + '\'' +
                ", fat='" + fat + '\'' +
                ", hiplie='" + hiplie + '\'' +
                ", pysical='" + pysical + '\'' +
                ", upArmGirth='" + upArmGirth + '\'' +
                ", upLegGirth='" + upLegGirth + '\'' +
                ", waistline='" + waistline + '\'' +
                ", weight='" + weight + '\'' +
                ", typeDate='" + typeDate + '\'' +
                '}';
    }
}
