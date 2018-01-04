package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jerry.guan on 11/22/2016.
 * 发送邀请模型
 */

public class SendInvitation {

    private long SenderId;
    private long InviterId;
    private String ClassId;
    private String ClassGroupId;
    private int ClassRole;
    private int Target;//0减重1增重

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
    }

    public long getSenderId() {
        return SenderId;
    }

    public void setSenderId(long senderId) {
        SenderId = senderId;
    }

    public long getInviterId() {
        return InviterId;
    }

    public void setInviterId(long inviterId) {
        InviterId = inviterId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassGroupId() {
        return ClassGroupId;
    }

    public void setClassGroupId(String classGroupId) {
        ClassGroupId = classGroupId;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int classRole) {
        ClassRole = classRole;
    }

}
