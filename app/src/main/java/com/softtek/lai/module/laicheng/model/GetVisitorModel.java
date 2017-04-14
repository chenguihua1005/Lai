package com.softtek.lai.module.laicheng.model;

/**
 * Created by shelly.xu on 4/14/2017.
 */

public class GetVisitorModel {
    private String recordId; //-----健康报告ID
    private float weight;//体重
    private String weightUnit;//斤
    private String bodyTypeTitle;//超重
    private String bodyTypeColor;//颜色
    private String BMI;
    private String bodyFatRate;
    private String viscusFatIndex;
    private String measuredTime;
   private GetModel visitor;

    public GetVisitorModel(String recordId, float weight, String weightUnit, String bodyTypeTitle, String bodyTypeColor, String BMI, String bodyFatRate, String viscusFatIndex, String measuredTime, GetModel visitor) {
        this.recordId = recordId;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.bodyTypeTitle = bodyTypeTitle;
        this.bodyTypeColor = bodyTypeColor;
        this.BMI = BMI;
        this.bodyFatRate = bodyFatRate;
        this.viscusFatIndex = viscusFatIndex;
        this.measuredTime = measuredTime;
        this.visitor = visitor;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getBodyTypeTitle() {
        return bodyTypeTitle;
    }

    public void setBodyTypeTitle(String bodyTypeTitle) {
        this.bodyTypeTitle = bodyTypeTitle;
    }

    public String getBodyTypeColor() {
        return bodyTypeColor;
    }

    public void setBodyTypeColor(String bodyTypeColor) {
        this.bodyTypeColor = bodyTypeColor;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getBodyFatRate() {
        return bodyFatRate;
    }

    public void setBodyFatRate(String bodyFatRate) {
        this.bodyFatRate = bodyFatRate;
    }

    public String getViscusFatIndex() {
        return viscusFatIndex;
    }

    public void setViscusFatIndex(String viscusFatIndex) {
        this.viscusFatIndex = viscusFatIndex;
    }

    public String getMeasuredTime() {
        return measuredTime;
    }

    public void setMeasuredTime(String measuredTime) {
        this.measuredTime = measuredTime;
    }

    public GetModel getVisitor() {
        return visitor;
    }

    public void setVisitor(GetModel visitor) {
        this.visitor = visitor;
    }

    @Override
    public String toString() {
        return "GetVisitorModel{" +
                "recordId='" + recordId + '\'' +
                ", weight=" + weight +
                ", weightUnit='" + weightUnit + '\'' +
                ", bodyTypeTitle='" + bodyTypeTitle + '\'' +
                ", bodyTypeColor='" + bodyTypeColor + '\'' +
                ", BMI='" + BMI + '\'' +
                ", bodyFatRate='" + bodyFatRate + '\'' +
                ", viscusFatIndex='" + viscusFatIndex + '\'' +
                ", measuredTime='" + measuredTime + '\'' +
                ", visitor=" + visitor +
                '}';
    }
}

