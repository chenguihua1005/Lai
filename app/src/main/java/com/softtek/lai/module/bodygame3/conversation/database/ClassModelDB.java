package com.softtek.lai.module.bodygame3.conversation.database;

/**
 * Created by jessica.zhang on 2016/12/16.
 */

public class ClassModelDB {
    private String ClassId;
    private String ClassName;
    private String ClassCode;
    private String HXGroupId;

    public ClassModelDB(String classId, String className, String classCode, String HXGroupId) {
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
