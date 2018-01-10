package com.softtek.lai.module.message2.model;

/**
 * 邀请确认展示模型
 * @author jerry.Guan
 *         created by 2016/12/1
 */

public class InvitationConfirmShow {


    private String MsgId;
    private String ClassId;
    private String ClassName;
    private String ClassMasterName;
    private long ClassMasterId;
    private String ClassCode;
    private String CGId;
    private String CGName;
    private int ClassRole;
    private String ClassStart;
    private String ClassMasterHxId;
    private String ClassHxGroupId;
    private String Sender;
    private long SenderId;
    private String ClassMasterPhoto;
    private String SenderPhoto;
    private int MsgStatus;
    private String IntroducerMobile;
    private int ClassStatus;

    private int Target;////学员目标 1增重0减重

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
    }

    public int getClassStatus() {
        return ClassStatus;
    }

    public void setClassStatus(int classStatus) {
        ClassStatus = classStatus;
    }

    public String getIntroducerMobile() {
        return IntroducerMobile;
    }

    public void setIntroducerMobile(String introducerMobile) {
        IntroducerMobile = introducerMobile;
    }

    public int getMsgStatus() {
        return MsgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        MsgStatus = msgStatus;
    }

    public String getClassMasterPhoto() {
        return ClassMasterPhoto;
    }

    public void setClassMasterPhoto(String classMasterPhoto) {
        ClassMasterPhoto = classMasterPhoto;
    }

    public String getSenderPhoto() {
        return SenderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        SenderPhoto = senderPhoto;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String MsgId) {
        this.MsgId = MsgId;
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

    public String getClassMasterName() {
        return ClassMasterName;
    }

    public void setClassMasterName(String ClassMasterName) {
        this.ClassMasterName = ClassMasterName;
    }

    public long getClassMasterId() {
        return ClassMasterId;
    }

    public void setClassMasterId(long ClassMasterId) {
        this.ClassMasterId = ClassMasterId;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String ClassCode) {
        this.ClassCode = ClassCode;
    }

    public String getCGId() {
        return CGId;
    }

    public void setCGId(String CGId) {
        this.CGId = CGId;
    }

    public String getCGName() {
        return CGName;
    }

    public void setCGName(String CGName) {
        this.CGName = CGName;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int ClassRole) {
        this.ClassRole = ClassRole;
    }

    public String getClassStart() {
        return ClassStart;
    }

    public void setClassStart(String ClassStart) {
        this.ClassStart = ClassStart;
    }

    public String getClassMasterHxId() {
        return ClassMasterHxId;
    }

    public void setClassMasterHxId(String ClassMasterHxId) {
        this.ClassMasterHxId = ClassMasterHxId;
    }

    public String getClassHxGroupId() {
        return ClassHxGroupId;
    }

    public void setClassHxGroupId(String ClassHxGroupId) {
        this.ClassHxGroupId = ClassHxGroupId;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public long getSenderId() {
        return SenderId;
    }

    public void setSenderId(long SenderId) {
        this.SenderId = SenderId;
    }

    @Override
    public String toString() {
        return "InvitationConfirmShow{" +
                "MsgId='" + MsgId + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassMasterName='" + ClassMasterName + '\'' +
                ", ClassMasterId=" + ClassMasterId +
                ", ClassCode='" + ClassCode + '\'' +
                ", CGId='" + CGId + '\'' +
                ", CGName='" + CGName + '\'' +
                ", ClassRole=" + ClassRole +
                ", ClassStart='" + ClassStart + '\'' +
                ", ClassMasterHxId=" + ClassMasterHxId +
                ", ClassHxGroupId=" + ClassHxGroupId +
                ", Sender='" + Sender + '\'' +
                ", SenderId=" + SenderId +
                ", ClassMasterPhoto='" + ClassMasterPhoto + '\'' +
                ", SenderPhoto='" + SenderPhoto + '\'' +
                ", MsgStatus=" + MsgStatus +
                '}';
    }
}
