package com.softtek.lai.module.sport.model;

/**
 * Created by jerry.guan on 8/2/2016.
 * 公里配速
 *
 */
public class KilometrePace {

    private String id;
    //没公里的经纬度
    private double longitude;
    private double latitude;
    //一公里的耗时
    private long kilometreTime;
    //改公里是否有问题
    private String hasProblem;
    private String index;
    private String iskilometre;

    private int step;
    private String speed;
    private String user;
    private long consumingTime;
    private double currentKM;


    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(long consumingTime) {
        this.consumingTime = consumingTime;
    }

    public double getCurrentKM() {
        return currentKM;
    }

    public void setCurrentKM(double currentKM) {
        this.currentKM = currentKM;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

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

    public long getKilometreTime() {
        return kilometreTime;
    }

    public void setKilometreTime(long kilometreTime) {
        this.kilometreTime = kilometreTime;
    }

    public String getHasProblem() {
        return hasProblem;
    }

    public void setHasProblem(String hasProblem) {
        this.hasProblem = hasProblem;
    }

    public String getIskilometre() {
        return iskilometre;
    }

    public void setIskilometre(String iskilometre) {
        this.iskilometre = iskilometre;
    }

    @Override
    public String toString() {
        return "KilometrePace{" +
                "id='" + id + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", kilometreTime=" + kilometreTime +
                ", hasProblem=" + hasProblem +
                ", index='" + index + '\'' +
                ", iskilometre=" + iskilometre +
                ", step=" + step +
                ", speed='" + speed + '\'' +
                ", user='" + user + '\'' +
                ", consumingTime=" + consumingTime +
                ", currentKM=" + currentKM +
                '}';
    }
}
