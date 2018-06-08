package com.softtek.lai.module.laisportmine.model;

import java.io.Serializable;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class ActionModel implements Serializable {
    private String MessageId;
    private String SentAcId;
    private String Content;
    private String SendTime;
    private String ActTitle;
    private String ActId;
    private String IsJoinAct;
    private String IsRead;
    private boolean isselect;

    @Override
    public String toString() {
        return "ActionModel{" +
                "MessageId='" + MessageId + '\'' +
                ", SentAcId='" + SentAcId + '\'' +
                ", Content='" + Content + '\'' +
                ", SendTime='" + SendTime + '\'' +
                ", ActTitle='" + ActTitle + '\'' +
                ", ActId='" + ActId + '\'' +
                ", IsJoinAct='" + IsJoinAct + '\'' +
                ", IsRead='" + IsRead + '\'' +
                ", isselect=" + isselect +
                '}';
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public boolean isselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
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

    public String getActTitle() {
        return ActTitle;
    }

    public void setActTitle(String actTitle) {
        ActTitle = actTitle;
    }

    public String getActId() {
        return ActId;
    }

    public void setActId(String actId) {
        ActId = actId;
    }

    public String getIsJoinAct() {
        return IsJoinAct;
    }

    public void setIsJoinAct(String isJoinAct) {
        IsJoinAct = isJoinAct;
    }
}
