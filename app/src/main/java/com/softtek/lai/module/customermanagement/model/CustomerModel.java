package com.softtek.lai.module.customermanagement.model;

import android.nfc.Tag;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

public class CustomerModel {
    private long AccountId;//用户编号/莱聚+账号 (市场人员使用)
    private String Mobile;//手机号码
    private String Name;//姓名
    private int Gender;//性别，0-男，1-女
    private float Height;//身高
    private String BirthDay;//出生日期
    private int Age;//年龄，周岁
    private boolean Tag;//标签 是否注册，true-已注册，false-未注册
    private String TagName;//是否注册描述
    private String Photo;//头像
    private String Creator;//添加人姓名
    private String CreatedTime;//添加时间
    private String Description;//描述

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

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

    public boolean isTag() {
        return Tag;
    }

    public void setTag(boolean tag) {
        Tag = tag;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "AccountId=" + AccountId +
                ", Mobile='" + Mobile + '\'' +
                ", Name='" + Name + '\'' +
                ", Gender=" + Gender +
                ", Height=" + Height +
                ", BirthDay='" + BirthDay + '\'' +
                ", Age=" + Age +
                ", Tag='" + Tag + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Creator='" + Creator + '\'' +
                ", CreatedTime='" + CreatedTime + '\'' +
                '}';
    }
}
