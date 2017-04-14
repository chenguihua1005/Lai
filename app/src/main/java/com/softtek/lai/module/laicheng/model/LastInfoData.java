package com.softtek.lai.module.laicheng.model;

/**
 * Created by jia.lu on 2017/4/14.
 */

public class LastInfoData {

    /**
     * recordId : 80bc0f53-e26f-46ab-87df-c4af6974f762
     * weight : 148.4
     * weightUnit : 斤
     * bodyTypeTitle : 超重
     * bodyTypeColor : ff6666
     * BMI : 25.7kg/m2
     * bodyFatRate : 31.9%
     * viscusFatIndex : 9.7
     * measuredTime : 2017年4月14日 09:20
     * visitor : {"Name":"欣欣","BirthDate":"1994-04-13","Age":23,"Gender":1,"Height":168,"PhoneNo":"18206182036"}
     */

    private String recordId;
    private double weight;
    private String weightUnit;
    private String bodyTypeTitle;
    private String bodyTypeColor;
    private String BMI;
    private String bodyFatRate;
    private String viscusFatIndex;
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

    public VisitorBean getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorBean visitor) {
        this.visitor = visitor;
    }

    public static class VisitorBean {
        /**
         * Name : 欣欣
         * BirthDate : 1994-04-13
         * Age : 23
         * Gender : 1
         * Height : 168
         * PhoneNo : 18206182036
         */

        private String Name;
        private String BirthDate;
        private int Age;
        private int Gender;
        private int Height;
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

        public int getHeight() {
            return Height;
        }

        public void setHeight(int Height) {
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
