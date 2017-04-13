package com.softtek.lai.module.laicheng.model;

/**
 * Created by shelly.xu on 2017/4/10.
 */

public class HistoryModel {
    private String time;
    private String name;
    private String phoneNo;
    private String age;
    private String gender;
    private String height;

    public HistoryModel(String time, String name, String phoneNo, String age, String gender, String height) {
        this.time = time;
        this.name = name;
        this.phoneNo = phoneNo;
        this.age = age;
        this.gender = gender;
        this.height = height;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
