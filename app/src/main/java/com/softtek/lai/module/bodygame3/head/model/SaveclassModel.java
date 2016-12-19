package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 12/19/2016.
 */

public class SaveclassModel {
    private String ClassId;
    private String ClassName;
    private String ClassCode;
    private int ClassRole;
    private String ClassWeek;

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
        return "SaveclassModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassCode='" + ClassCode + '\'' +
                ", ClassRole=" + ClassRole +
                ", ClassWeek='" + ClassWeek + '\'' +
                '}';
    }
}
