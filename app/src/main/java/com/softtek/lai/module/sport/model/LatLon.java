package com.softtek.lai.module.sport.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 6/3/2016.
 */
public class LatLon implements Parcelable{
    private double longitude;
    private double latitude;

    public LatLon(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LatLon() {
    }

    protected LatLon(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<LatLon> CREATOR = new Creator<LatLon>() {
        @Override
        public LatLon createFromParcel(Parcel in) {
            return new LatLon(in);
        }

        @Override
        public LatLon[] newArray(int size) {
            return new LatLon[size];
        }
    };

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



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }
}
