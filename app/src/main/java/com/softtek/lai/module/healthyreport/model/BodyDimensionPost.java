package com.softtek.lai.module.healthyreport.model;

/**
 * Created by jia.lu on 3/15/2018.
 */

public class BodyDimensionPost {
    private String RecordId;
    private float Circum;
    private float Waistline;
    private float Hiplie;
    private float UpArmGirth;
    private float UpLegGirth;
    private float DoLegGirth;

    public String getRecordId() {
        return RecordId;
    }

    public void setRecordId(String recordId) {
        RecordId = recordId;
    }

    public float getCircum() {
        return Circum;
    }

    public void setCircum(float circum) {
        Circum = circum;
    }

    public float getWaistline() {
        return Waistline;
    }

    public void setWaistline(float waistline) {
        Waistline = waistline;
    }

    public float getHiplie() {
        return Hiplie;
    }

    public void setHiplie(float hiplie) {
        Hiplie = hiplie;
    }

    public float getUpArmGirth() {
        return UpArmGirth;
    }

    public void setUpArmGirth(float upArmGirth) {
        UpArmGirth = upArmGirth;
    }

    public float getUpLegGirth() {
        return UpLegGirth;
    }

    public void setUpLegGirth(float upLegGirth) {
        UpLegGirth = upLegGirth;
    }

    public float getDoLegGirth() {
        return DoLegGirth;
    }

    public void setDoLegGirth(float doLegGirth) {
        DoLegGirth = doLegGirth;
    }
}
