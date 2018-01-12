package com.softtek.lai.module.bodygame3.head.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class ClassModel implements Parcelable{
    private String ClassId;
    private String ClassName;
    private String ClassCode;
    private int ClassRole;//role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员"
    private String ClassWeek;
    private boolean IsWorker;//是否工作人员。true-是，false-否

    public boolean isWorker() {
        return IsWorker;
    }

    public void setWorker(boolean worker) {
        IsWorker = worker;
    }

    public static Creator<ClassModel> getCREATOR() {
        return CREATOR;
    }

    public ClassModel(String classId, String className, String classCode, int classRole, String classWeek, boolean isWorker) {
        ClassId = classId;
        ClassName = className;
        ClassCode = classCode;
        ClassRole = classRole;
        ClassWeek = classWeek;
        IsWorker = isWorker;
    }

    public ClassModel() {

    }

    protected ClassModel(Parcel in) {
        ClassId = in.readString();
        ClassName = in.readString();
        ClassCode = in.readString();
        ClassRole = in.readInt();
        ClassWeek = in.readString();
    }

    public static final Creator<ClassModel> CREATOR = new Creator<ClassModel>() {
        @Override
        public ClassModel createFromParcel(Parcel in) {
            return new ClassModel(in);
        }

        @Override
        public ClassModel[] newArray(int size) {
            return new ClassModel[size];
        }
    };

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int classRole) {
        ClassRole = classRole;
    }

    public String getClassWeek() {
        return ClassWeek;
    }

    public void setClassWeek(String classWeek) {
        ClassWeek = classWeek;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassCode='" + ClassCode + '\'' +
                ", ClassRole='" + ClassRole + '\'' +
                ", ClassWeek='" + ClassWeek + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ClassId);
        dest.writeString(ClassName);
        dest.writeString(ClassCode);
        dest.writeInt(ClassRole);
        dest.writeString(ClassWeek);
    }
}
