package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by jerry.guan on 11/22/2016.
 */

public class ClassInvitater {

    private String ClassId;
    private String ClassName;
    private String ClassCode;
    private String StartDate;
    private long InviterMLAccountId;
    private String InviterMLUserName;
    private String InviterPhoto;
    private String InviterName;
    private String InviterMobile;
    private String ClassGroupHxId;
    private int IsCurrentClassMember;
    private String CurrentClassGroup;
    private String CurrentCGId;
    private String CurrentClassRole;

    public int getIsCurrentClassMember() {
        return IsCurrentClassMember;
    }

    public void setIsCurrentClassMember(int isCurrentClassMember) {
        IsCurrentClassMember = isCurrentClassMember;
    }

    public String getCurrentClassGroup() {
        return CurrentClassGroup;
    }

    public void setCurrentClassGroup(String currentClassGroup) {
        CurrentClassGroup = currentClassGroup;
    }

    public String getCurrentCGId() {
        return CurrentCGId;
    }

    public void setCurrentCGId(String currentCGId) {
        CurrentCGId = currentCGId;
    }

    public String getCurrentClassRole() {
        return CurrentClassRole;
    }

    public void setCurrentClassRole(String currentClassRole) {
        CurrentClassRole = currentClassRole;
    }

    public String getClassGroupHxId() {
        return ClassGroupHxId;
    }

    public void setClassGroupHxId(String classGroupHxId) {
        ClassGroupHxId = classGroupHxId;
    }

    private List<ClassGroup> ClassGroupList;
    private List<ClassRole> ClassRole;

    public String getInviterName() {
        return InviterName;
    }

    public void setInviterName(String inviterName) {
        InviterName = inviterName;
    }

    public String getInviterMobile() {
        return InviterMobile;
    }

    public void setInviterMobile(String inviterMobile) {
        InviterMobile = inviterMobile;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String ClassId) {
        this.ClassId = ClassId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String ClassCode) {
        this.ClassCode = ClassCode;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public long getInviterMLAccountId() {
        return InviterMLAccountId;
    }

    public void setInviterMLAccountId(long InviterMLAccountId) {
        this.InviterMLAccountId = InviterMLAccountId;
    }

    public String getInviterMLUserName() {
        return InviterMLUserName;
    }

    public void setInviterMLUserName(String InviterMLUserName) {
        this.InviterMLUserName = InviterMLUserName;
    }

    public String getInviterPhoto() {
        return InviterPhoto;
    }

    public void setInviterPhoto(String InviterPhoto) {
        this.InviterPhoto = InviterPhoto;
    }

    public List<ClassGroup> getClassGroupList() {
        return ClassGroupList;
    }

    public void setClassGroupList(List<ClassGroup> classGroupList) {
        ClassGroupList = classGroupList;
    }

    public List<com.softtek.lai.module.bodygame3.more.model.ClassRole> getClassRole() {
        return ClassRole;
    }

    public void setClassRole(List<com.softtek.lai.module.bodygame3.more.model.ClassRole> classRole) {
        ClassRole = classRole;
    }

    @Override
    public String toString() {
        return "ClassInvitater{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassCode='" + ClassCode + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", InviterMLAccountId=" + InviterMLAccountId +
                ", InviterMLUserName='" + InviterMLUserName + '\'' +
                ", InviterPhoto='" + InviterPhoto + '\'' +
                ", InviterName='" + InviterName + '\'' +
                ", InviterMobile='" + InviterMobile + '\'' +
                ", ClassGroupList=" + ClassGroupList +
                ", ClassRole=" + ClassRole +
                '}';
    }
}
