package com.softtek.lai.module.newmemberentry.view.model;

import java.io.Serializable;

/**
 * Created by julie.zhu on 3/21/2016.
 */
public class Newstudents implements Serializable {

    private String nickname;    //昵称:必填
    private String certification;//资格证号: 选填
    private String mobile;  //手机号码: 必填
    private String ClassId; //参赛班级:必填
    private String weight;  //初始体重:必填
    private String pysical; //体脂:选填
    private String fat;     //内脂:选填
    private String birthday; //生日:必填
    private String gender;      //性别 :gender(int 0女，1男)必填
                            //照片上传: 选填
    private String circum;      //胸围:可空
    private String waistline;   //腰围:,可空
    private String hiplie;      //臀围:可空
    private String uparmgirth;//上臂围:,可空
    private String upleggirth;//大腿围 :,可空
    private String doleggirth;//小腿围:,可空


    public Newstudents(String nickname, String certification, String mobile, String classid, String weight, String pysical, String fat, String birthday, String gender) {
        this.nickname = nickname;
        this.certification = certification;
        this.mobile = mobile;
        this.ClassId = classid;
        this.weight = weight;
        this.pysical = pysical;
        this.fat = fat;
        this.birthday = birthday;
        this.gender = gender;
    }
    public Newstudents( String circum, String waistline, String hiplie, String uparmgirth, String upleggirth, String doleggirth) {
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String ClassId) {
        this.ClassId = ClassId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPysical() {
        return pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCircum() {
        return circum;
    }

    public void setCircum(String circum) {
        this.circum = circum;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHiplie() {
        return hiplie;
    }

    public void setHiplie(String hiplie) {
        this.hiplie = hiplie;
    }

    public String getUparmgirth() {
        return uparmgirth;
    }

    public void setUparmgirth(String uparmgirth) {
        this.uparmgirth = uparmgirth;
    }

    public String getUpleggirth() {
        return upleggirth;
    }

    public void setUpleggirth(String upleggirth) {
        this.upleggirth = upleggirth;
    }

    public String getDoleggirth() {
        return doleggirth;
    }

    public void setDoleggirth(String doleggirth) {
        this.doleggirth = doleggirth;
    }

    @Override
    public String toString() {
        return "Newstudents{" +
                "nickname='" + nickname + '\'' +
                ", certification='" + certification + '\'' +
                ", mobile='" + mobile + '\'' +
                ", classid='" + ClassId + '\'' +
                ", weight='" + weight + '\'' +
                ", pysical='" + pysical + '\'' +
                ", fat='" + fat + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", circum='" + circum + '\'' +
                ", waistline='" + waistline + '\'' +
                ", hiplie='" + hiplie + '\'' +
                ", uparmgirth='" + uparmgirth + '\'' +
                ", upleggirth='" + upleggirth + '\'' +
                ", doleggirth='" + doleggirth + '\'' +
                '}';
    }
}
