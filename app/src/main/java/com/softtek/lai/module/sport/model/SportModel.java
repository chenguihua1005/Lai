package com.softtek.lai.module.sport.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 7/28/2016.
 * 记录运动轨迹数据
 */
public class SportModel implements Parcelable{

    private String id;
    private String user;
    private double longitude;
    private double latitude;

    private String speed;
    private boolean iskilometre;
    private long consumingTime;
    private boolean hasProblem;
    private double currentKM;
    private long kilometreTime;
    private int step;
    private String index;

    public SportModel() {
    }

    protected SportModel(Parcel in) {
        id = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        speed = in.readString();
        iskilometre = in.readByte() != 0;
        consumingTime = in.readLong();
        hasProblem=in.readByte()!=0;
        step=in.readInt();
        currentKM=in.readDouble();
        kilometreTime=in.readLong();
        user=in.readString();
        index=in.readString();

    }

    public static final Creator<SportModel> CREATOR = new Creator<SportModel>() {
        @Override
        public SportModel createFromParcel(Parcel in) {
            return new SportModel(in);
        }

        @Override
        public SportModel[] newArray(int size) {
            return new SportModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public boolean iskilometre() {
        return iskilometre;
    }

    public void setIskilometre(boolean iskilometre) {
        this.iskilometre = iskilometre;
    }

    public long getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(long consumingTime) {
        this.consumingTime = consumingTime;
    }

    public boolean isHasProblem() {
        return hasProblem;
    }

    public void setHasProblem(boolean hasProblem) {
        this.hasProblem = hasProblem;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public double getCurrentKM() {
        return currentKM;
    }

    public void setCurrentKM(double currentKM) {
        this.currentKM = currentKM;
    }

    public long getKilometreTime() {
        return kilometreTime;
    }

    public void setKilometreTime(long kilometreTime) {
        this.kilometreTime = kilometreTime;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(speed);
        dest.writeByte((byte) (iskilometre ? 1 : 0));
        dest.writeLong(consumingTime);
        dest.writeByte((byte)(hasProblem?1:0));
        dest.writeInt(step);
        dest.writeDouble(currentKM);
        dest.writeLong(kilometreTime);
        dest.writeString(user);
        dest.writeString(index);
    }
}
