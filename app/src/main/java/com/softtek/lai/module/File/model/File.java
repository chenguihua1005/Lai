package com.softtek.lai.module.File.model;

import java.io.Serializable;

/**
 * Created by julie.zhu on 3/4/2016.
 */
public class File implements Serializable{

     private String nickname; //昵称
     private String birthday; //生日yyyy-MM-dd
     private int height;      // 身高
     private int weight;      //体重
     private int gender;      //性别 :gender(int 0女，1男)

     private double circum;      //胸围:可空
     private double waistline;   //(腰围:,可空)
     private double hiplie;      //臀围:可空
     private double uparmgirth;//(上臂围:,可空)
     private double upleggirth;//(大腿围 :,可空)
     private double doleggirth;//(小腿围:,可空)

    public File(String s){

    }

    public File(String nickname, String birthday, int height, int weight, int gender) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.gender = gender;

    }
    public File(double circum, double waistline, double hiplie,double uparmgirth, double upleggirth, double doleggirth) {
        this.circum = circum;
        this.waistline = waistline;
        this.hiplie = hiplie;
        this.uparmgirth = uparmgirth;
        this.upleggirth = upleggirth;
        this.doleggirth = doleggirth;
    }
    public String getNickname() {
        return nickname;
    }

    public String getBrithday() {
        return birthday;
    }

    public void setBrithday(String brithday) {
        this.birthday = brithday;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getGender() {
        return gender;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getCircum() {
        return circum;
    }

    public double getWaistline() {
        return waistline;
    }

    public double getHiplie() {
        return hiplie;
    }

    public double getUparmgirth() {
        return uparmgirth;
    }

    public double getUpleggirth() {
        return upleggirth;
    }

    public double getDoleggirth() {
        return doleggirth;
    }

    public void setCircum(double circum) {
        this.circum = circum;
    }

    public void setWaistline(double waistline) {
        this.waistline = waistline;
    }

    public void setHiplie(double hiplie) {
        this.hiplie = hiplie;
    }

    public void setUparmgirth(double uparmgirth) {
        this.uparmgirth = uparmgirth;
    }

    public void setUpleggirth(double upleggirth) {
        this.upleggirth = upleggirth;
    }

    public void setDoleggirth(double doleggirth) {
        this.doleggirth = doleggirth;
    }

    @Override
    public String toString() {
        return "File{" +
                "nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", gender=" + gender +
                ", circum=" + circum +
                ", waistline=" + waistline +
                ", hiplie=" + hiplie +
                ", uparmgirth=" + uparmgirth +
                ", upleggirth=" + upleggirth +
                ", doleggirth=" + doleggirth +
                '}';
    }
}
