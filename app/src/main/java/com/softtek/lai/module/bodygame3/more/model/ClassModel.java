package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by jerry.guan on 11/17/2016.
 * 班级模型
 */

public class ClassModel {

    private String ClassId;
    private String ClassCode;
    private String ClassName;
    private int ClassRole;//班级角色1:总教练,2:教练,3:助教,4:学员
    private String ClassMasterName;
    private List<String> ClassMeasureDateList;

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int classRole) {
        ClassRole = classRole;
    }

    public String getClassMasterName() {
        return ClassMasterName;
    }

    public void setClassMasterName(String classMasterName) {
        ClassMasterName = classMasterName;
    }

    public List<String> getClassMeasureDateList() {
        return ClassMeasureDateList;
    }

    public void setClassMeasureDateList(List<String> classMeasureDateList) {
        ClassMeasureDateList = classMeasureDateList;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassCode='" + ClassCode + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassRole=" + ClassRole +
                ", ClassMasterName='" + ClassMasterName + '\'' +
                ", ClassMeasureDateList=" + ClassMeasureDateList +
                '}';
    }
}
