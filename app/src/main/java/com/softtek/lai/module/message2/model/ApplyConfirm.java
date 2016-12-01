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
    private List<ClassGroup> ClassGroups;
    private List<ClassRole> ClassRoles;

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
}
