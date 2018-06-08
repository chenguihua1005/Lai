/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ApplyAssistantModel implements Serializable {

    private String ClassId;
    private String ClassName;
    private String ManagerId;
    private String StartDate;
    private String EndDate;
    private String num;
    private String CreateDate;
    private String State;

    @Override
    public String toString() {
        return "ApplyAssistantModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ManagerId='" + ManagerId + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", num='" + num + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", State='" + State + '\'' +
                '}';
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
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

    public String getManagerId() {
        return ManagerId;
    }

    public void setManagerId(String managerId) {
        ManagerId = managerId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

}
