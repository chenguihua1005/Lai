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
    private boolean hasProblem;
    private String index;
    private boolean iskilometre;

    public boolean iskilometre() {
        return iskilometre;
    }

    public void setIskilometre(boolean iskilometre) {
        this.iskilometre = iskilometre;
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

    public boolean isHasProblem() {
        return hasProblem;
    }

    public void setHasProblem(boolean hasProblem) {
        this.hasProblem = hasProblem;
    }
}
