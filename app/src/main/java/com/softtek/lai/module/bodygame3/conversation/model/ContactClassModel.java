package com.softtek.lai.module.bodygame3.conversation.model;

import java.io.Serializable;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

public class ContactClassModel implements Serializable {

    private long CoachId;//总教练Id
    private String CoachName;//总教练姓名
    private String CoachPhoto;//总教练照片

    private String ClassId;
    private String ClassBanner;
    private String ClassName;
    private String ClassCode;//班级编号

    private String Role;
    private String StartDate;
    private String EndDate;
    private int Total;
    private String HXGroupId;
    private int Status;//0 状态: 0:无解散班级权限， 1：有解散班级权限

    public ContactClassModel() {
    }

    public ContactClassModel(long coachId, String coachName, String coachPhoto, String classId, String classBanner, String className, String classCode, String role, String startDate, String endDate, int total, String HXGroupId, int status) {
        CoachId = coachId;
        CoachName = coachName;
        CoachPhoto = coachPhoto;
        ClassId = classId;
        ClassBanner = classBanner;
        ClassName = className;
        ClassCode = classCode;
        Role = role;
        StartDate = startDate;
        EndDate = endDate;
        Total = total;
        this.HXGroupId = HXGroupId;
        Status = status;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getHXGroupId() {
        return HXGroupId;
    }

    public void setHXGroupId(String HXGroupId) {
        this.HXGroupId = HXGroupId;
    }

    public long getCoachId() {
        return CoachId;
    }

    public void setCoachId(long coachId) {
        CoachId = coachId;
    }

    public String getCoachName() {
        return CoachName;
    }

    public void setCoachName(String coachName) {
        CoachName = coachName;
    }

    public String getCoachPhoto() {
        return CoachPhoto;
    }

    public void setCoachPhoto(String coachPhoto) {
        CoachPhoto = coachPhoto;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassBanner() {
        return ClassBanner;
    }

    public void setClassBanner(String classBanner) {
        ClassBanner = classBanner;
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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
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

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
