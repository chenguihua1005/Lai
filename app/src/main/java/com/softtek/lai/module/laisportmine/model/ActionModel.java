package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class ActionModel {
    private String MessageId;
    private String SentAcId;
    private String Content;
    private String SendTime;

    @Override
    public String toString() {
        return "ActionModel{" +
                "MessageId='" + MessageId + '\'' +
                ", SentAcId='" + SentAcId + '\'' +
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

    public String getSentAcId() {
        return SentAcId;
    }

    public void setSentAcId(String sentAcId) {
        SentAcId = sentAcId;
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
