/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.model;

import java.io.Serializable;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class OperateMsgModel implements Serializable {
    private String MsgType;
    private String MsgId;
    private String ReceId;
    private String SenderId;
    private String Content;
    private String ClassId;
    private String SendTime;
    private String IsRead;
    private String IsDo;

    @Override
    public String toString() {
        return "OperateMsgModel{" +
                "MsgType='" + MsgType + '\'' +
                ", MsgId='" + MsgId + '\'' +
                ", ReceId='" + ReceId + '\'' +
                ", SenderId='" + SenderId + '\'' +
                ", Content='" + Content + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", SendTime='" + SendTime + '\'' +
                ", IsRead='" + IsRead + '\'' +
                ", IsDo='" + IsDo + '\'' +
                '}';
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getReceId() {
        return ReceId;
    }

    public void setReceId(String receId) {
        ReceId = receId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public String getIsDo() {
        return IsDo;
    }

    public void setIsDo(String isDo) {
        IsDo = isDo;
    }
}
