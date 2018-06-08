package com.softtek.lai.module.bodygame3.more.model;

import java.io.Serializable;

/**
 * 历史班级模型
 * @author jerry.Guan
 *         created by 2016/12/2
 */

public class HistoryClassModel implements Serializable{

    public String ClassId;
    public String ClassName;
    public String ClassStart;
    public String ClassEnd;
    public String MasterPhoto;
    public String MasterName;

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

    public String getClassStart() {
        return ClassStart;
    }

    public void setClassStart(String classStart) {
        ClassStart = classStart;
    }

    public String getClassEnd() {
        return ClassEnd;
    }

    public void setClassEnd(String classEnd) {
        ClassEnd = classEnd;
    }

    public String getMasterPhoto() {
        return MasterPhoto;
    }

    public void setMasterPhoto(String masterPhoto) {
        MasterPhoto = masterPhoto;
    }

    public String getMasterName() {
        return MasterName;
    }

    public void setMasterName(String masterName) {
        MasterName = masterName;
    }
}
