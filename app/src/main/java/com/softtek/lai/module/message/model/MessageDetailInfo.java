/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MessageDetailInfo implements Serializable {

    private String MsgType;
    private String InviterId;
    private String SenderId;
    private String Comments;
    private String ClassId;
    private String SendTime;
    private String IsRead;
    private String MessageId;

    @Override
    public String toString() {
        return "MessageDetailInfo{" +
                "MsgType='" + MsgType + '\'' +
                ", InviterId='" + InviterId + '\'' +
                ", SenderId='" + SenderId + '\'' +
                ", Comments='" + Comments + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", SendTime='" + SendTime + '\'' +
                ", IsRead='" + IsRead + '\'' +
                ", MessageId='" + MessageId + '\'' +
                '}';
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public String getInviterId() {
        return InviterId;
    }

    public void setInviterId(String inviterId) {
        InviterId = inviterId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

}
