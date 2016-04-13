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

    private String InviterId;
    private String SenderId;
    private String Comments;
    private String ClassId;

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

    @Override
    public String toString() {
        return "MessageDetailInfo{" +
                "InviterId='" + InviterId + '\'' +
                ", SenderId='" + SenderId + '\'' +
                ", Comments='" + Comments + '\'' +
                ", ClassId='" + ClassId + '\'' +
                '}';
    }
}
