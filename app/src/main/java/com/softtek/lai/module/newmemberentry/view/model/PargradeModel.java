/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.model;

/**
 * Created by julie.zhu on 3/23/2016.
 */
public class PargradeModel {
    private String ClassId;
    private String ClassName;

    @Override
    public String toString() {
        return "PargradeModel{" +
                "ClassIdModel='" + ClassId + '\'' +
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

    public PargradeModel(String classId, String className) {
        ClassId = classId;
        ClassName = className;
    }
}
