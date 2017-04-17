package com.softtek.lai.module.laicheng.model;

/**
 * Created by shelly.xu on 4/14/2017.
 */

public class VisitorInfoModel {
    private String Name;//访客姓名
    private String BirthDate;//访客生日
    private int Age;//访客年龄
    private int Gender;//访客性别
    private float Height;//访客身高
    private String PhoneNo;//访客手机号

    public VisitorInfoModel(String name, String birthDate, int age, int gender, float height, String phoneNo) {
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
}
