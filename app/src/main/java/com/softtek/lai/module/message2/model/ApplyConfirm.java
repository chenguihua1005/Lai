package com.softtek.lai.module.message2.model;

import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.model.ClassRole;

import java.util.List;

/**
 * @author jerry.Guan
 *         created by 2016/12/1
 */

public class ApplyConfirm {

    private int ApplyId;
    private String ApplyName;
    private String ApplyCert;
    private String ApplyMobile;
    private int ApplyMLId;
    private String ApplyMLName;
    private String ApplyHxId;
    private String ClassHxId;
    private String ClassId;
    private int MsgStatus;
    private String ClassGroupName;
    private String ClassGroupId;//已经加入的组别Id
    private String ClassRoleName;
    private int ClassRoleValue;//所在小组的角色值
    private List<ClassGroup> ClassGroups;
    private List<ClassRole> ClassRoles;
    private String ClassName;
    private String ClassCode;
    private String ApplyPhoto;
    private int Target;//学员目标 1增重0减重

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
    }

    public String getClassGroupId() {
        return ClassGroupId;
    }

    public void setClassGroupId(String classGroupId) {
        ClassGroupId = classGroupId;
    }

    public int getClassRoleValue() {
        return ClassRoleValue;
    }

    public void setClassRoleValue(int classRoleValue) {
        ClassRoleValue = classRoleValue;
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

    public String getClassGroupName() {
        return ClassGroupName;
    }

    public void setClassGroupName(String classGroupName) {
        ClassGroupName = classGroupName;
    }

    public String getClassRoleName() {
        return ClassRoleName;
    }

    public void setClassRoleName(String classRoleName) {
        ClassRoleName = classRoleName;
    }

    public int getMsgStatus() {
        return MsgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        MsgStatus = msgStatus;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public int getApplyId() {
        return ApplyId;
    }

    public void setApplyId(int ApplyId) {
        this.ApplyId = ApplyId;
    }

    public String getApplyName() {
        return ApplyName;
    }

    public void setApplyName(String ApplyName) {
        this.ApplyName = ApplyName;
    }

    public String getApplyCert() {
        return ApplyCert;
    }

    public void setApplyCert(String ApplyCert) {
        this.ApplyCert = ApplyCert;
    }

    public String getApplyMobile() {
        return ApplyMobile;
    }

    public void setApplyMobile(String ApplyMobile) {
        this.ApplyMobile = ApplyMobile;
    }

    public int getApplyMLId() {
        return ApplyMLId;
    }

    public void setApplyMLId(int ApplyMLId) {
        this.ApplyMLId = ApplyMLId;
    }

    public String getApplyMLName() {
        return ApplyMLName;
    }

    public void setApplyMLName(String ApplyMLName) {
        this.ApplyMLName = ApplyMLName;
    }

    public String getApplyHxId() {
        return ApplyHxId;
    }

    public void setApplyHxId(String ApplyHxId) {
        this.ApplyHxId = ApplyHxId;
    }

    public String getClassHxId() {
        return ClassHxId;
    }

    public void setClassHxId(String ClassHxId) {
        this.ClassHxId = ClassHxId;
    }

    public List<ClassGroup> getClassGroups() {
        return ClassGroups;
    }

    public void setClassGroups(List<ClassGroup> classGroups) {
        ClassGroups = classGroups;
    }

    public List<ClassRole> getClassRoles() {
        return ClassRoles;
    }

    public void setClassRoles(List<ClassRole> classRoles) {
        ClassRoles = classRoles;
    }

    public String getApplyPhoto() {
        return ApplyPhoto;
    }

    public void setApplyPhoto(String applyPhoto) {
        ApplyPhoto = applyPhoto;
    }

    @Override
    public String toString() {
        return "ApplyConfirm{" +
                "ApplyId=" + ApplyId +
                ", ApplyName='" + ApplyName + '\'' +
                ", ApplyCert='" + ApplyCert + '\'' +
                ", ApplyMobile='" + ApplyMobile + '\'' +
                ", ApplyMLId=" + ApplyMLId +
                ", ApplyMLName='" + ApplyMLName + '\'' +
                ", ApplyHxId='" + ApplyHxId + '\'' +
                ", ClassHxId='" + ClassHxId + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", MsgStatus=" + MsgStatus +
                ", ClassGroupName='" + ClassGroupName + '\'' +
                ", ClassGroupId='" + ClassGroupId + '\'' +
                ", ClassRoleName='" + ClassRoleName + '\'' +
                ", ClassRoleValue=" + ClassRoleValue +
                ", ClassGroups=" + ClassGroups +
                ", ClassRoles=" + ClassRoles +
                ", ClassName='" + ClassName + '\'' +
                ", ClassCode='" + ClassCode + '\'' +
                '}';
    }
}
