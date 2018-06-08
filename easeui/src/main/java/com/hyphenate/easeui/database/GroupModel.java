package com.hyphenate.easeui.database;

/**
 * Created by jessica.zhang on 1/23/2017.
 */

public class GroupModel {
    private String ClassId;
    private String ClassName;
    private String ClassCode;//班级编号
    private String HXGroupId;//班级环信ID

    public GroupModel() {
    }

    public GroupModel(String classId, String className, String classCode, String HXGroupId) {
        ClassId = classId;
        ClassName = className;
        ClassCode = classCode;
        this.HXGroupId = HXGroupId;
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

    public String getHXGroupId() {
        return HXGroupId;
    }

    public void setHXGroupId(String HXGroupId) {
        this.HXGroupId = HXGroupId;
    }
}
