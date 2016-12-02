package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class ClassModel {
//    "ClassId": "c4e8e179-fd99-4955-8bf9-cf470898788b",
//    "ClassName": "班级2",
//    "ClassCode": "123457",
//    "ClassRole": "4",		————1：开班教练	2：组别教练	3：组别助教	4：学员
//    "ClassWeek": "8"		————班级所在周
    private String ClassId;
    private String ClassName;
    private String ClassCode;
    private int ClassRole;
    private String ClassWeek;

    public ClassModel(String classId, String className, String classCode, int classRole, String classWeek) {
        ClassId = classId;
        ClassName = className;
        ClassCode = classCode;
        ClassRole = classRole;
        ClassWeek = classWeek;
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
