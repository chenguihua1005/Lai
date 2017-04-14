package com.softtek.lai.module.laicheng.model;

/**
 * Created by shelly.xu on 4/14/2017.
 */

public class GetModel {
    private String Name;
    private String BirthDate;
    private int Age;
    private int Gender;
    private float Height;
    private String PhoneNo;

    public GetModel(String name, String birthDate, int age, int gender, float height, String phoneNo) {
        Name = name;
        BirthDate = birthDate;
        Age = age;
        Gender = gender;
        Height = height;
        PhoneNo = phoneNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public float getHeight() {
        return Height;
    }

    public void setHeight(float height) {
        Height = height;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "GetModel{" +
                "Name='" + Name + '\'' +
                ", BirthDate='" + BirthDate + '\'' +
                ", Age=" + Age +
                ", Gender=" + Gender +
                ", Height=" + Height +
                ", PhoneNo='" + PhoneNo + '\'' +
                '}';
    }
}
