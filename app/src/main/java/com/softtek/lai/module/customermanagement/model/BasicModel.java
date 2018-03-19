package com.softtek.lai.module.customermanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class BasicModel implements Parcelable {
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
    private int Age;
    private boolean IsSuperior;

    public boolean isSuperior() {
        return IsSuperior;
    }

    public void setSuperior(boolean superior) {
        IsSuperior = superior;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    protected BasicModel(Parcel in) {
        AccountId = in.readLong();
        Name = in.readString();
        BirthDay = in.readString();
        Photo = in.readString();
        Mobile = in.readString();
        Certification = in.readString();
        UserRole = in.readString();
        Gender = in.readString();
        Angel = in.readString();
        Height = in.readFloat();
        Weight = in.readFloat();
        Age = in.readInt();
        IsSuperior = in.readByte() != 0;
    }

    public static final Creator<BasicModel> CREATOR = new Creator<BasicModel>() {
        @Override
        public BasicModel createFromParcel(Parcel in) {
            return new BasicModel(in);
        }

        @Override
        public BasicModel[] newArray(int size) {
            return new BasicModel[size];
        }
    };

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
                ", Age=" + Age +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(AccountId);
        parcel.writeString(Name);
        parcel.writeString(BirthDay);
        parcel.writeString(Photo);
        parcel.writeString(Mobile);
        parcel.writeString(Certification);
        parcel.writeString(UserRole);
        parcel.writeString(Gender);
        parcel.writeString(Angel);
        parcel.writeFloat(Height);
        parcel.writeFloat(Weight);
        parcel.writeInt(Age);
        parcel.writeByte((byte) (IsSuperior ? 1 : 0));
    }
}
