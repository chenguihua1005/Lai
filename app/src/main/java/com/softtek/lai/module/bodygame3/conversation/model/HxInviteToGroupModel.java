package com.softtek.lai.module.bodygame3.conversation.model;

/**
 * Created by jessica.zhang on 1/18/2017.
 */

public class HxInviteToGroupModel {
//    "JoinClassHxId": "602985ba-fb6d-453d-b7fd-fc9b6e365a05", --记录Id
//    "MessageId": "04f6761d-4f19-4eae-a4c9-8da49514893f",--消息Id
//    "ClassId": "98724a68-db89-4852-8cf7-98c22a9bb9e4",--班级Id
//    "Status": false,--状态
//    "ApplyId": 92025,--当前用户
//    "ClassGroupHxId": "1484728088629",--班级环信Id
//    "CoachId": 81885,--教练Id
//    "CoachHxId": "a8b88eaf7fcbacc3303b78d24dd9973e" –教练环信

    private String JoinClassHxId;//记录Id
    private String MessageId;//消息Id
    private String ClassId;//班级Id
    private boolean Status;//状态
    private long ApplyId;//当前用户
    private String ClassGroupHxId;//班级环信Id
    private long CoachId;//教练Id
    private String CoachHxId; //教练环信id

    public String getJoinClassHxId() {
        return JoinClassHxId;
    }

    public void setJoinClassHxId(String joinClassHxId) {
        JoinClassHxId = joinClassHxId;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public long getApplyId() {
        return ApplyId;
    }

    public void setApplyId(long applyId) {
        ApplyId = applyId;
    }

    public String getClassGroupHxId() {
        return ClassGroupHxId;
    }

    public void setClassGroupHxId(String classGroupHxId) {
        ClassGroupHxId = classGroupHxId;
    }

    public long getCoachId() {
        return CoachId;
    }

    public void setCoachId(long coachId) {
        CoachId = coachId;
    }

    public String getCoachHxId() {
        return CoachHxId;
    }

    public void setCoachHxId(String coachHxId) {
        CoachHxId = coachHxId;
    }
}
