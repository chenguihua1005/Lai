package com.softtek.lai.module.bodygame3.more.model;

/**
 * 邀请人列表模型
 * @author jerry.Guan
 *         created by 2016/11/21
 */

public class InvitatedContact {


    /**
     * MessageId : fd2bebec-2587-471b-a8f9-40a68b661a6e
     * InviterId : 77215
     * InviterPhoto : 201610282026390465458463.jpg
     * InviterUserName : Willian
     * InviterMobile : 18961765466
     * InviterCertification : CN1765466
     * JoinGroupName : 小组1
     * JoinGroupId : c3757697-25bb-4340-b040-f76e9a21ab90
     * ClassRole : 2
     * InviterStatus : 0
     */

    private String MessageId;
    private int InviterId;
    private String InviterPhoto;
    private String InviterUserName;
    private String InviterMobile;
    private String InviterCertification;
    private String JoinGroupName;
    private String JoinGroupId;
    private int ClassRole;
    private int InviterStatus;

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String MessageId) {
        this.MessageId = MessageId;
    }

    public int getInviterId() {
        return InviterId;
    }

    public void setInviterId(int InviterId) {
        this.InviterId = InviterId;
    }

    public String getInviterPhoto() {
        return InviterPhoto;
    }

    public void setInviterPhoto(String InviterPhoto) {
        this.InviterPhoto = InviterPhoto;
    }

    public String getInviterUserName() {
        return InviterUserName;
    }

    public void setInviterUserName(String InviterUserName) {
        this.InviterUserName = InviterUserName;
    }

    public String getInviterMobile() {
        return InviterMobile;
    }

    public void setInviterMobile(String InviterMobile) {
        this.InviterMobile = InviterMobile;
    }

    public String getInviterCertification() {
        return InviterCertification;
    }

    public void setInviterCertification(String InviterCertification) {
        this.InviterCertification = InviterCertification;
    }

    public String getJoinGroupName() {
        return JoinGroupName;
    }

    public void setJoinGroupName(String JoinGroupName) {
        this.JoinGroupName = JoinGroupName;
    }

    public String getJoinGroupId() {
        return JoinGroupId;
    }

    public void setJoinGroupId(String JoinGroupId) {
        this.JoinGroupId = JoinGroupId;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int ClassRole) {
        this.ClassRole = ClassRole;
    }

    public int getInviterStatus() {
        return InviterStatus;
    }

    public void setInviterStatus(int InviterStatus) {
        this.InviterStatus = InviterStatus;
    }
}
