package com.softtek.lai.module.healthyreport.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 4/14/2017.
 */

public class HealthyItem implements Parcelable{


    /**
     * title : 体重
     * value : 45.3
     * unit : kg
     * caption : 偏瘦
     * color : ff6666
     */
    private int pid;
    private String title;
    private String value;
    private String unit;
    private String caption;
    private String color;

    protected HealthyItem(Parcel in) {
        pid = in.readInt();
        title = in.readString();
        value = in.readString();
        unit = in.readString();
        caption = in.readString();
        color = in.readString();
    }

    public static final Creator<HealthyItem> CREATOR = new Creator<HealthyItem>() {
        @Override
        public HealthyItem createFromParcel(Parcel in) {
            return new HealthyItem(in);
        }

        @Override
        public HealthyItem[] newArray(int size) {
            return new HealthyItem[size];
        }
    };

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeString(title);
        dest.writeString(value);
        dest.writeString(unit);
        dest.writeString(caption);
        dest.writeString(color);
    }
}
