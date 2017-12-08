package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class BasicModel {
    private long AccountId;
    private String Name;
    private String BirthDay;
    private String Photo;
    private String Mobile;
    private String Certification;
    private String UserRole;
    private String Gender;
    private String Angel;//爱心天使
    private float Height;
    private float Weight;

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String certification) {
        Certification = certification;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAngel() {
        return Angel;
    }

    public void setAngel(String angel) {
        Angel = angel;
    }

    public float getHeight() {
        return Height;
    }

    public void setHeight(float height) {
        Height = height;
    }

    public float getWeight() {
        return Weight;
    }

    public void setWeight(float weight) {
        Weight = weight;
    }

    @Override
    public String toString() {
        return "BasicModel{" +
                "AccountId=" + AccountId +
                ", Name='" + Name + '\'' +
                ", BirthDay='" + BirthDay + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Certification='" + Certification + '\'' +
                ", UserRole='" + UserRole + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Angel='" + Angel + '\'' +
                ", Height=" + Height +
                ", Weight=" + Weight +
                '}';
    }
}
