package com.softtek.lai.module.sport.model;

/**
 * Created by jerry.guan on 6/3/2016.
 */
public class LatLon {
    private double longitude;
    private double latitude;

    public LatLon(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LatLon() {
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
}
