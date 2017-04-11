package com.softtek.lai.module.laicheng.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;

/**
 * Created by shelly.xu on 4/10/2017.
 */

public class VisitorModel  {
    private String Name;
    private int Age;//年龄
    private float Height;//身高
    private int Gender;//性别
    private String PhoneNo;//手机号



    public VisitorModel(String name, int age, float height, int gender, String phoneNo) {
        Name = name;
        Age = age;
        Height = height;
        Gender = gender;
        PhoneNo = phoneNo;
    }

    public VisitorModel() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public float getHeight() {
        return Height;
    }

    public void setHeight(float height) {
        Height = height;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "VisitorModel{" +
                "Name='" + Name + '\'' +
                ", Age=" + Age +
                ", Height=" + Height +
                ", Gender=" + Gender +
                ", PhoneNo='" + PhoneNo + '\'' +
                '}';
    }


}
