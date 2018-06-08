/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.model;

import java.io.Serializable;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class NoticeMsgModel implements Serializable {
    private String MessageType;
    private String MessageId;
    private String Content;
    private String SendTime;
    private String IsRead;

    public NoticeMsgModel(String messageType, String messageId, String content, String sendTime, String isRead) {
        MessageType = messageType;
        MessageId = messageId;
        Content = content;
        SendTime = sendTime;
        IsRead = isRead;
    }

    @Override
    public String toString() {
        return "NoticeMsgModel{" +
                "MessageType='" + MessageType + '\'' +
                ", MessageId='" + MessageId + '\'' +
                ", Content='" + Content + '\'' +
                ", SendTime='" + SendTime + '\'' +
                ", IsRead='" + IsRead + '\'' +
                '}';
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
}
