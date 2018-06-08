/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.model;

import java.io.Serializable;

/**
 * Created by julie.zhu on 3/4/2016.
 */
public class FileModel implements Serializable {

    private String nickname; //昵称
    private String birthday; //生日yyyy-MM-dd
    private double height;      // 身高
    private double weight;      //体重
    private int gender;      //性别 :gender(int 0女，1男)

    private double circum;      //胸围:可空
    private double waistline;   //(腰围:,可空)
    private double hiplie;      //臀围:可空
    private double uparmgirth;//(上臂围:,可空)
    private double upleggirth;//(大腿围 :,可空)
    private double doleggirth;//(小腿围:,可空)


    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }



    public FileModel() {

    }

    public String getNickname() {
        return nickname;
    }


    public String getBirthday() {
        return birthday;
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
        return "FileModel{" +
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
