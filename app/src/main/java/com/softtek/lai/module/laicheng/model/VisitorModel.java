package com.softtek.lai.module.laicheng.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;

/**
 * Created by shelly.xu on 4/10/2017.
 */

public class VisitorModel  {
    private String Name;
    private String BirthDate;//年龄
    private float Height;//身高
    private int Gender;//性别
    private String PhoneNo;//
    private int visitorId;

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public VisitorModel(String name, String BirthDate, float height, int gender, String phoneNo) {
        Name = name;
        BirthDate = BirthDate;
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

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
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
                ", BirthDate=" + BirthDate +
                ", Height=" + Height +
                ", Gender=" + Gender +
                ", PhoneNo='" + PhoneNo + '\'' +
                '}';
    }


}
