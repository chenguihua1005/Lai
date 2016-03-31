/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ClassInfoModel implements Serializable {

    private String ClassId;     //班级id
    private String CityId;  //城市id
    private String ClassName;     //班级名称
    private String ClassStatus;    //班级状态
    private String CreateDate;    //开班日期
    private String EndDate;    //结束日期
    private String StartDate;    //开始日期
    private String ManagerId;    //开班顾问id
    private String MemberCount;    //开班顾问id

    public ClassInfoModel(String classId, String cityId, String className, String classStatus, String createDate, String endDate, String startDate, String managerId, String memberCount) {
        ClassId = classId;
        CityId = cityId;
        ClassName = className;
        ClassStatus = classStatus;
        CreateDate = createDate;
        EndDate = endDate;
        StartDate = startDate;
        ManagerId = managerId;
        MemberCount = memberCount;
    }

    public String getClassId() {
        return ClassId;
    }

    public String getCityId() {
        return CityId;
    }

    public String getClassName() {
        return ClassName;
    }

    public String getClassStatus() {
        return ClassStatus;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getManagerId() {
        return ManagerId;
    }

    public String getMemberCount() {
        return MemberCount;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public void setClassStatus(String classStatus) {
        ClassStatus = classStatus;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public void setManagerId(String managerId) {
        ManagerId = managerId;
    }

    public void setMemberCount(String memberCount) {
        MemberCount = memberCount;
    }

    @Override
    public String toString() {
        return "ClassInfoModel{" +
                "ClassIdModel='" + ClassId + '\'' +
                ", CityId='" + CityId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassStatus='" + ClassStatus + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", ManagerId='" + ManagerId + '\'' +
                ", MemberCount='" + MemberCount + '\'' +
                '}';
    }
}
