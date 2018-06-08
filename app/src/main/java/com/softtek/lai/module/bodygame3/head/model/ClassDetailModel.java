package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by lareina.qiao on 12/13/2016.
 */
public class ClassDetailModel {
    private String ClassId;//班级id
    private String ClassName;//班级名称
    private String ClassCode;//班级编号
    private String ClassMasterId;//教练id
    private String ClassMasterPhoto;//教练头像
    private String ClassMasterName;//教练姓名
    private String ClassStart;//开班时间
    private String ClassMemberNum;//学员人数
    private String IsSendMsg;//是否发送，1是已发送
    private int Target;//学员目标 1增重0减重

    @Override
    public String toString() {
        return "ClassDetailModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassCode='" + ClassCode + '\'' +
                ", ClassMasterId='" + ClassMasterId + '\'' +
                ", ClassMasterPhoto='" + ClassMasterPhoto + '\'' +
                ", ClassMasterName='" + ClassMasterName + '\'' +
                ", ClassStart='" + ClassStart + '\'' +
                ", ClassMemberNum='" + ClassMemberNum + '\'' +
                ", IsSendMsg='" + IsSendMsg + '\'' +
                ", Target=" + Target +
                '}';
    }

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
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

    public String getClassMasterId() {
        return ClassMasterId;
    }

    public void setClassMasterId(String classMasterId) {
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

    public String getClassMemberNum() {
        return ClassMemberNum;
    }

    public void setClassMemberNum(String classMemberNum) {
        ClassMemberNum = classMemberNum;
    }

    public String getIsSendMsg() {
        return IsSendMsg;
    }

    public void setIsSendMsg(String isSendMsg) {
        IsSendMsg = isSendMsg;
    }
}
