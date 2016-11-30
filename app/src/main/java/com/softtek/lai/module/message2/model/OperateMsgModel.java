/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.model;

import java.io.Serializable;

public class OperateMsgModel implements Serializable {
    private int Msgtype;
    private String Msgid;
    private String Recevid;
    private String Senderid;
    private String MsgContent;
    private String Classid;
    private String SendTime;
    private int IsRead;
    private int IsDo;

    public int getMsgtype() {
        return Msgtype;
    }

    public void setMsgtype(int msgtype) {
        Msgtype = msgtype;
    }

    public String getMsgid() {
        return Msgid;
    }

    public void setMsgid(String msgid) {
        Msgid = msgid;
    }

    public String getRecevid() {
        return Recevid;
    }

    public void setRecevid(String recevid) {
        Recevid = recevid;
    }

    public String getSenderid() {
        return Senderid;
    }

    public void setSenderid(String senderid) {
        Senderid = senderid;
    }

    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String msgContent) {
        MsgContent = msgContent;
    }

    public String getClassid() {
        return Classid;
    }

    public void setClassid(String classid) {
        Classid = classid;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }

    public int getIsDo() {
        return IsDo;
    }

    public void setIsDo(int isDo) {
        IsDo = isDo;
    }
}
