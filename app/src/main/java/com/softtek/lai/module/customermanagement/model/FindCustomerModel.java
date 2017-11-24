package com.softtek.lai.module.customermanagement.model;

import java.io.Serializable;

/**
 * Created by jessica.zhang on 11/24/2017.
 */

public class FindCustomerModel implements Serializable{
    private String Mobile;
    private String Name;
    private int Gender;//1.	性别统一为0-男，1-女
    private String BirthDay;
    private int Height;
    private int Weight;
    private long Creator;//创建者帐号
    private String CreatedTime;
    private long Editor;//编辑者帐号
    private String UpdatedTime;
    private long AccountId;

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

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public long getCreator() {
        return Creator;
    }

    public void setCreator(long creator) {
        Creator = creator;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public long getEditor() {
        return Editor;
    }

    public void setEditor(long editor) {
        Editor = editor;
    }

    public String getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        UpdatedTime = updatedTime;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    @Override
    public String toString() {
        return "FindCustomerModel{" +
                "Mobile='" + Mobile + '\'' +
                ", Name='" + Name + '\'' +
                ", Gender=" + Gender +
                ", BirthDay='" + BirthDay + '\'' +
                ", Height=" + Height +
                ", Weight=" + Weight +
                ", Creator=" + Creator +
                ", CreatedTime='" + CreatedTime + '\'' +
                ", Editor=" + Editor +
                ", UpdatedTime='" + UpdatedTime + '\'' +
                ", AccountId=" + AccountId +
                '}';
    }
}
