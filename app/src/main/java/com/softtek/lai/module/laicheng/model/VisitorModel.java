package com.softtek.lai.module.laicheng.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shelly.xu on 4/10/2017.
 */

public class VisitorModel implements Parcelable {
    private String Name;
    private String BirthDate;//年龄
    private float Height;//身高
    private int Gender;//性别
    private String PhoneNo;//
    private long visitorId;
    private String classId;
    private int age;
    private boolean isSuperior;
    private int source = -1;//从哪个tab页面来的：-1没有，0意向客户，1市场人员，2体管班

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public boolean isSuperior() {
        return isSuperior;
    }

    public void setSuperior(boolean superior) {
        isSuperior = superior;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    protected VisitorModel(Parcel in) {
        Name = in.readString();
        BirthDate = in.readString();
        Height = in.readFloat();
        Gender = in.readInt();
        PhoneNo = in.readString();
        visitorId = in.readLong();
        isSuperior = in.readByte() != 0;
    }

    public static final Creator<VisitorModel> CREATOR = new Creator<VisitorModel>() {
        @Override
        public VisitorModel createFromParcel(Parcel in) {
            return new VisitorModel(in);
        }

        @Override
        public VisitorModel[] newArray(int size) {
            return new VisitorModel[size];
        }
    };


    public long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(long visitorId) {
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
                ", BirthDate='" + BirthDate + '\'' +
                ", Height=" + Height +
                ", Gender=" + Gender +
                ", PhoneNo='" + PhoneNo + '\'' +
                ", visitorId=" + visitorId +
                ", classId='" + classId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(BirthDate);
        parcel.writeFloat(Height);//身高
        parcel.writeInt(Gender);//性别
        parcel.writeString(PhoneNo);//
        parcel.writeLong(visitorId);
        parcel.writeByte((byte)(isSuperior ? 1 : 0));
    }
}
