package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class SystemNewsModel {
    private String MessageId;
    private String Content;
    private String SendTime;

    @Override
    public String toString() {
        return "SystemNewsModel{" +
                "MessageId='" + MessageId + '\'' +
                ", Content='" + Content + '\'' +
                ", SendTime='" + SendTime + '\'' +
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
}
