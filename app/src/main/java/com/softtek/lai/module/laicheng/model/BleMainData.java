package com.softtek.lai.module.laicheng.model;

import java.io.Serializable;

/**
 * Created by jia.lu on 2017/4/5.
 */

public class BleMainData implements Serializable{

    /**
     * recordId : 95b952c3-7863-4ed6-8414-0cbfb1e8ebef
     * weight : 148.4
     * weightUnit : 斤
     * bodyTypeTitle : 超重
     * bodyTypeColor : ff6666
     * BMI : 25.7
     * BMIUnit : kg/m2
     * bodyFat : 18.7
     * bodyFatUnit : kg
     * bodyFatRate : 25.2
     * bodyFatRateUnit : %
     * viscusFatIndex : 7.2
     * fatFreemass : 55.5
     * fatFreemassUnit : kg
     * waterContent : 40.8
     * waterContentUnit : kg
     * waterContentRate : 55
     * waterContentRateUnit : “%”
     * bonemass : 3
     * bonemassUnit : kg
     * musclemass : 51.7
     * musclemassUnit : kg
     * basalmetabolicrate : 1576.1
     * basalmetabolicrateUnit : kcal/day
     * physicalAge : 31.8
     * measuredTime : 2017年4月20日 17:55
     * visitor : {"Name":"翁总哦","BirthDate":"1992-04-13","Age":25,"Gender":0,"Height":170,"PhoneNo":""}
     */

    private String recordId;
    private double weight;
    private String weightUnit;
    private String bodyTypeTitle;
    private String bodyTypeColor;
    private String BMI;
    private String BMIUnit;
    private String bodyFat;
    private String bodyFatUnit;
    private String bodyFatRate;
    private String bodyFatRateUnit;
    private String viscusFatIndex;
    private String fatFreemass;
    private String fatFreemassUnit;
    private String waterContent;
    private String waterContentUnit;
    private String waterContentRate;
    private String waterContentRateUnit;
    private String bonemass;
    private String bonemassUnit;
    private String musclemass;
    private String musclemassUnit;
    private String basalmetabolicrate;
    private String basalmetabolicrateUnit;
    private String physicalAge;
    private String measuredTime;
    private VisitorBean visitor;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
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

    public String getBMIUnit() {
        return BMIUnit;
    }

    public void setBMIUnit(String BMIUnit) {
        this.BMIUnit = BMIUnit;
    }

    public String getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(String bodyFat) {
        this.bodyFat = bodyFat;
    }

    public String getBodyFatUnit() {
        return bodyFatUnit;
    }

    public void setBodyFatUnit(String bodyFatUnit) {
        this.bodyFatUnit = bodyFatUnit;
    }

    public String getBodyFatRate() {
        return bodyFatRate;
    }

    public void setBodyFatRate(String bodyFatRate) {
        this.bodyFatRate = bodyFatRate;
    }

    public String getBodyFatRateUnit() {
        return bodyFatRateUnit;
    }

    public void setBodyFatRateUnit(String bodyFatRateUnit) {
        this.bodyFatRateUnit = bodyFatRateUnit;
    }

    public String getViscusFatIndex() {
        return viscusFatIndex;
    }

    public void setViscusFatIndex(String viscusFatIndex) {
        this.viscusFatIndex = viscusFatIndex;
    }

    public String getFatFreemass() {
        return fatFreemass;
    }

    public void setFatFreemass(String fatFreemass) {
        this.fatFreemass = fatFreemass;
    }

    public String getFatFreemassUnit() {
        return fatFreemassUnit;
    }

    public void setFatFreemassUnit(String fatFreemassUnit) {
        this.fatFreemassUnit = fatFreemassUnit;
    }

    public String getWaterContent() {
        return waterContent;
    }

    public void setWaterContent(String waterContent) {
        this.waterContent = waterContent;
    }

    public String getWaterContentUnit() {
        return waterContentUnit;
    }

    public void setWaterContentUnit(String waterContentUnit) {
        this.waterContentUnit = waterContentUnit;
    }

    public String getWaterContentRate() {
        return waterContentRate;
    }

    public void setWaterContentRate(String waterContentRate) {
        this.waterContentRate = waterContentRate;
    }

    public String getWaterContentRateUnit() {
        return waterContentRateUnit;
    }

    public void setWaterContentRateUnit(String waterContentRateUnit) {
        this.waterContentRateUnit = waterContentRateUnit;
    }

    public String getBonemass() {
        return bonemass;
    }

    public void setBonemass(String bonemass) {
        this.bonemass = bonemass;
    }

    public String getBonemassUnit() {
        return bonemassUnit;
    }

    public void setBonemassUnit(String bonemassUnit) {
        this.bonemassUnit = bonemassUnit;
    }

    public String getMusclemass() {
        return musclemass;
    }

    public void setMusclemass(String musclemass) {
        this.musclemass = musclemass;
    }

    public String getMusclemassUnit() {
        return musclemassUnit;
    }

    public void setMusclemassUnit(String musclemassUnit) {
        this.musclemassUnit = musclemassUnit;
    }

    public String getBasalmetabolicrate() {
        return basalmetabolicrate;
    }

    public void setBasalmetabolicrate(String basalmetabolicrate) {
        this.basalmetabolicrate = basalmetabolicrate;
    }

    public String getBasalmetabolicrateUnit() {
        return basalmetabolicrateUnit;
    }

    public void setBasalmetabolicrateUnit(String basalmetabolicrateUnit) {
        this.basalmetabolicrateUnit = basalmetabolicrateUnit;
    }

    public String getPhysicalAge() {
        return physicalAge;
    }

    public void setPhysicalAge(String physicalAge) {
        this.physicalAge = physicalAge;
    }

    public String getMeasuredTime() {
        return measuredTime;
    }

    public void setMeasuredTime(String measuredTime) {
        this.measuredTime = measuredTime;
    }

    public VisitorBean getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorBean visitor) {
        this.visitor = visitor;
    }

    public static class VisitorBean {
        /**
         * Name : 翁总哦
         * BirthDate : 1992-04-13
         * Age : 25
         * Gender : 0
         * Height : 170
         * PhoneNo :
         */

        private String Name;
        private String BirthDate;
        private int Age;
        private int Gender;
        private float Height;
        private String PhoneNo;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getBirthDate() {
            return BirthDate;
        }

        public void setBirthDate(String BirthDate) {
            this.BirthDate = BirthDate;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public int getGender() {
            return Gender;
        }

        public void setGender(int Gender) {
            this.Gender = Gender;
        }

        public float getHeight() {
            return Height;
        }

        public void setHeight(float Height) {
            this.Height = Height;
        }

        public String getPhoneNo() {
            return PhoneNo;
        }

        public void setPhoneNo(String PhoneNo) {
            this.PhoneNo = PhoneNo;
        }
    }
}
