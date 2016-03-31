/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class AssistantClassInfo implements Serializable {

    private String ClassId;     //班级Id
    private String ClassName;     //班级名字
    private String StartDate;        //开班时间
    private String Cnt;        //助教人数

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

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getCnt() {
        return Cnt;
    }

    public void setCnt(String cnt) {
        Cnt = cnt;
    }

    @Override
    public String toString() {
        return "AssistantClassInfo{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", Cnt='" + Cnt + '\'' +
                '}';
    }
}
