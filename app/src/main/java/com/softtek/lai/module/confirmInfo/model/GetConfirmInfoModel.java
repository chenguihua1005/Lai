package com.softtek.lai.module.confirmInfo.model;

/**
 * Created by julie.zhu on 4/15/2016.
 */
public class GetConfirmInfoModel {

    private String AccountId;
    private String UserName;
    private String Mobile;
    private String Birthday;
    private String Gender;
    private String Photo;
    private String ClassName;
    private String Weight;
    private String Pysical;
    private String Fat;
    private String Circum;
    private String Waistline;
    private String Hiplie;
    private String UpArmGirth;
    private String UpLegGirth;
    private String DoLegGirth;

    public GetConfirmInfoModel(String accountId, String userName, String mobile, String birthday, String gender, String photo, String className, String weight, String pysical, String fat, String circum, String waistline, String hiplie, String upArmGirth, String upLegGirth, String doLegGirth) {
        AccountId = accountId;
        UserName = userName;
        Mobile = mobile;
        Birthday = birthday;
        Gender = gender;
        Photo = photo;
        ClassName = className;
        Weight = weight;
        Pysical = pysical;
        Fat = fat;
        Circum = circum;
        Waistline = waistline;
        Hiplie = hiplie;
        UpArmGirth = upArmGirth;
        UpLegGirth = upLegGirth;
        DoLegGirth = doLegGirth;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getPysical() {
        return Pysical;
    }

    public void setPysical(String pysical) {
        Pysical = pysical;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getCircum() {
        return Circum;
    }

    public void setCircum(String circum) {
        Circum = circum;
    }

    public String getWaistline() {
        return Waistline;
    }

    public void setWaistline(String waistline) {
        Waistline = waistline;
    }

    public String getHiplie() {
        return Hiplie;
    }

    public void setHiplie(String hiplie) {
        Hiplie = hiplie;
    }

    public String getUpArmGirth() {
        return UpArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        UpArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return UpLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        UpLegGirth = upLegGirth;
    }

    public String getDoLegGirth() {
        return DoLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        DoLegGirth = doLegGirth;
    }

    @Override
    public String toString() {
        return "GetConfirmInfoModel{" +
                "AccountId='" + AccountId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Birthday='" + Birthday + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Photo='" + Photo + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Pysical='" + Pysical + '\'' +
                ", Fat='" + Fat + '\'' +
                ", Circum='" + Circum + '\'' +
                ", Waistline='" + Waistline + '\'' +
                ", Hiplie='" + Hiplie + '\'' +
                ", UpArmGirth='" + UpArmGirth + '\'' +
                ", UpLegGirth='" + UpLegGirth + '\'' +
                ", DoLegGirth='" + DoLegGirth + '\'' +
                '}';
    }
}
