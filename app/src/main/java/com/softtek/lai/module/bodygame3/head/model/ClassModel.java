package com.softtek.lai.module.bodygame3.head.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class ClassModel{
    private String ClassId;
    private String ClassName;
    private String ClassCode;
    private int ClassRole;//role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员"
    private String ClassWeek;

    public ClassModel(String classId, String className, String classCode, int classRole, String classWeek) {
        ClassId = classId;
        ClassName = className;
        ClassCode = classCode;
        ClassRole = classRole;
        ClassWeek = classWeek;
    }

    public ClassModel() {

    }

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


}
