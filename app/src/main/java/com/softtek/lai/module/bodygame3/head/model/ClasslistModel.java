package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class ClasslistModel {
    private String ClassId;
    private String ClassName;
    private String ClassCode;//班级编号
    private int ClassMasterId;//班级总教练id
    private String ClassMasterPhoto;//总教练照片
    private String ClassMasterName;//总教练名称
    private String ClassStart;
    private int ClassMemberNum;//学员人数

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

    public int getClassMasterId() {
        return ClassMasterId;
    }

    public void setClassMasterId(int classMasterId) {
        ClassMasterId = classMasterId;
    }

    public String getClassMasterPhoto() {
        return ClassMasterPhoto;
    }

    public void setClassMasterPhoto(String classMasterPhoto) {
        ClassMasterPhoto = classMasterPhoto;
    }

    public String getClassMasterName() {
        return ClassMasterName;
    }

    public void setClassMasterName(String classMasterName) {
        ClassMasterName = classMasterName;
    }

    public String getClassStart() {
        return ClassStart;
    }

    public void setClassStart(String classStart) {
        ClassStart = classStart;
    }

    public int getClassMemberNum() {
        return ClassMemberNum;
    }

    public void setClassMemberNum(int classMemberNum) {
        ClassMemberNum = classMemberNum;
    }
}
