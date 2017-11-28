package com.softtek.lai.module.customermanagement.model;

import java.io.Serializable;

/**
 * Created by jessica.zhang on 11/24/2017.
 */

public class CustomerInfoModel implements Serializable{
    private String Mobile;
    private String Name;
    private int Gender;//1.	性别统一为0-男，1-女
    private String BirthDay;
    private double Height;
    private double Weight;
    private String Remark;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @Override
    public String toString() {
        return "CustomerInfoModel{" +
                "Mobile='" + Mobile + '\'' +
                ", Name='" + Name + '\'' +
                ", Gender=" + Gender +
                ", BirthDay='" + BirthDay + '\'' +
                ", Height=" + Height +
                ", Weight=" + Weight +
                ", Remark='" + Remark + '\'' +
                '}';
    }
}
