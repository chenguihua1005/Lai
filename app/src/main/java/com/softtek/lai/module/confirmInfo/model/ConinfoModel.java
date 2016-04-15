package com.softtek.lai.module.confirmInfo.model;

/**
 * Created by zcy on 2016/4/13.
 */
public class ConinfoModel {
    private long accountid;
    private String classid;
    private String nickname;
    private String birthday;
    private int gender;
    private String photo;
    private double weight;
    private double pysical;
    private double fat;
    private double circum;
    private double  waistline;
    private double  hiplie;
    private double  uparmgirth;
    private double  upleggirth;
    private double doleggirth;

    public ConinfoModel(long accountid, String classid, String nickname, String birthday, int gender, String photo, double weight, double pysical, double fat, double circum, double waistline, double hiplie, double uparmgirth, double upleggirth, double doleggirth) {
        this.accountid = accountid;
        this.classid = classid;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.photo = photo;
        this.weight = weight;
        this.pysical = pysical;
        this.fat = fat;
        this.circum = circum;
        this.waistline = waistline;
        this.hiplie = hiplie;
        this.uparmgirth = uparmgirth;
        this.upleggirth = upleggirth;
        this.doleggirth = doleggirth;
    }
    public ConinfoModel() {

    }
    public long getAccountid() {
        return accountid;
    }

    public void setAccountid(long accountid) {
        this.accountid = accountid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    @Override
    public String toString() {
        return "ConinfoModel{" +
                "accountid=" + accountid +
                ", classid='" + classid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender=" + gender +
                ", photo='" + photo + '\'' +
                ", weight=" + weight +
                ", pysical=" + pysical +
                ", fat=" + fat +
                ", circum=" + circum +
                ", waistline=" + waistline +
                ", hiplie=" + hiplie +
                ", uparmgirth=" + uparmgirth +
                ", upleggirth=" + upleggirth +
                ", doleggirth=" + doleggirth +
                '}';
    }
}
