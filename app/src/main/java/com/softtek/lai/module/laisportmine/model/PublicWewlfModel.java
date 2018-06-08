package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/11/2016.
 */
public class PublicWewlfModel {
    private String MessageId;
    private String Content;
    private String SendTime;
    private String IsRead;

    @Override
    public String toString() {
        return "PublicWewlfModel{" +
                "MessageId='" + MessageId + '\'' +
                ", Content='" + Content + '\'' +
                ", SendTime='" + SendTime + '\'' +
                ", IsRead='" + IsRead + '\'' +
                '}';
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
