package com.softtek.lai.module.bodygame3.more.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 11/22/2016.
 * 班级小组模型
 */

public class ClassGroup implements Parcelable{

    private String CGId;
    private String CGName;

    public ClassGroup() {
    }

    protected ClassGroup(Parcel in) {
        CGId = in.readString();
        CGName = in.readString();
    }

    public static final Creator<ClassGroup> CREATOR = new Creator<ClassGroup>() {
        @Override
        public ClassGroup createFromParcel(Parcel in) {
            return new ClassGroup(in);
        }

        @Override
        public ClassGroup[] newArray(int size) {
            return new ClassGroup[size];
        }
    };

    public String getCGId() {
        return CGId;
    }

    public void setCGId(String CGId) {
        this.CGId = CGId;
    }

    public String getCGName() {
        return CGName;
    }

    public void setCGName(String CGName) {
        this.CGName = CGName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(CGId);
        parcel.writeString(CGName);
    }

    @Override
    public String toString() {
        return "ClassGroup{" +
                "CGId='" + CGId + '\'' +
                ", CGName='" + CGName + '\'' +
                '}';
    }
}
