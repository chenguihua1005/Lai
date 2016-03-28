package com.softtek.lai.module.newmemberentry.view.model;

/**
 * Created by julie.zhu on 3/23/2016.
 */
public class Pargrade {
    private String ClassId;
    private String ClassName;

    @Override
    public String toString() {
        return "Pargrade{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                '}';
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

    public Pargrade(String classId, String className) {
        ClassId = classId;
        ClassName = className;
    }
}
