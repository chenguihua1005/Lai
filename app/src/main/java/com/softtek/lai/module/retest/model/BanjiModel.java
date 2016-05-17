/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.model;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public class BanjiModel {

    private long id;
    private long ClassId;
    private String ClassName;
    private long ManagerId;
    private String StartDate;
    private int Total;
    private String Rest;

    @Override
    public String toString() {
        return "BanjiModel{" +
                "id=" + id +
                ", ClassId=" + ClassId +
                ", ClassName='" + ClassName + '\'' +
                ", ManagerId=" + ManagerId +
                ", StartDate='" + StartDate + '\'' +
                ", Total=" + Total +
                ", Rest='" + Rest + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClassId() {
        return ClassId;
    }

    public void setClassId(long classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public long getManagerId() {
        return ManagerId;
    }

    public void setManagerId(long managerId) {
        ManagerId = managerId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }


    public String getRest() {
        return Rest;
    }

    public void setRest(String rest) {
        Rest = rest;
    }

    public BanjiModel(String startDate, String className, int total) {

        ClassName = className;
        StartDate = startDate;
        Total = total;
    }

    public BanjiModel(long id, long classId, String className, long managerId, String startDate, int total) {
        this.id = id;
        ClassId = classId;
        ClassName = className;
        ManagerId = managerId;
        StartDate = startDate;
        Total = total;
    }


}
