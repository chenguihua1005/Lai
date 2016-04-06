/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.model;

import java.io.Serializable;

/**
 * Created by julie.zhu on 3/21/2016.
 */
public class NewstudentsModel implements Serializable {

    private long sentaccid; //Sp id
    private String nickname;    //昵称:必填
   // private String certification;//资格证号: 选填
    private String password;    //密码：手机号码后6位
    private String mobile;  //手机号码: 必填
    private int classid; //参赛班级:必填
    private double weight;  //初始体重:必填
    private double pysical; //体脂:选填
    private double fat;     //内脂:选填
    private String birthday; //生日:必填
    private int gender;      //性别 :gender(int 0女，1男)必填
    private String photo;
    private double circum;      //胸围:可空
    private double waistline;   //腰围:,可空
    private double hiplie;      //臀围:可空
    private double uparmgirth;//上臂围:,可空
    private double upleggirth;//大腿围 :,可空
    private double doleggirth;//小腿围:,可空

    @Override
    public String toString() {
        return "NewstudentsModel{" +
                "sentaccid=" + sentaccid +
                ", nickname='" + nickname + '\'' +
                ", password=" + password +
                ", mobile='" + mobile + '\'' +
                ", classid='" + classid + '\'' +
                ", weight=" + weight +
                ", pysical=" + pysical +
                ", fat=" + fat +
                ", birthday='" + birthday + '\'' +
                ", gender=" + gender +
                ", photo='" + photo + '\'' +
                ", circum=" + circum +
                ", waistline=" + waistline +
                ", hiplie=" + hiplie +
                ", uparmgirth=" + uparmgirth +
                ", upleggirth=" + upleggirth +
                ", doleggirth=" + doleggirth +
                '}';
    }

    public long getSentaccid() {
        return sentaccid;
    }

    public void setSentaccid(long sentaccid) {
        this.sentaccid = sentaccid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPysical() {
        return pysical;
    }

    public void setPysical(double pysical) {
        this.pysical = pysical;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getCircum() {
        return circum;
    }

    public void setCircum(double circum) {
        this.circum = circum;
    }

    public double getWaistline() {
        return waistline;
    }

    public void setWaistline(double waistline) {
        this.waistline = waistline;
    }

    public double getHiplie() {
        return hiplie;
    }

    public void setHiplie(double hiplie) {
        this.hiplie = hiplie;
    }

    public double getUparmgirth() {
        return uparmgirth;
    }

    public void setUparmgirth(double uparmgirth) {
        this.uparmgirth = uparmgirth;
    }

    public double getUpleggirth() {
        return upleggirth;
    }

    public void setUpleggirth(double upleggirth) {
        this.upleggirth = upleggirth;
    }

    public double getDoleggirth() {
        return doleggirth;
    }

    public void setDoleggirth(double doleggirth) {
        this.doleggirth = doleggirth;
    }

    public NewstudentsModel(long sentaccid, String nickname, String password, String mobile, int classid, double weight, double pysical, double fat, String birthday, int gender, String photo, double circum, double waistline, double hiplie, double uparmgirth, double upleggirth, double doleggirth) {
        this.sentaccid = sentaccid;
        this.nickname = nickname;
        this.password = password;
        this.mobile = mobile;
        this.classid = classid;
        this.weight = weight;
        this.pysical = pysical;
        this.fat = fat;
        this.birthday = birthday;
        this.gender = gender;
        this.photo = photo;
        this.circum = circum;
        this.waistline = waistline;
        this.hiplie = hiplie;
        this.uparmgirth = uparmgirth;
        this.upleggirth = upleggirth;
        this.doleggirth = doleggirth;
    }

    public NewstudentsModel() {

    }
}
