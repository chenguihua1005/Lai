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
    private int MsgStatus;//0默认 1接受 2拒绝
    private String SenderPhoto;
    private boolean isSelected;
    private String MsgTitle;

    public String getMsgTitle() {
        return MsgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        MsgTitle = msgTitle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSenderPhoto() {
        return SenderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        SenderPhoto = senderPhoto;
    }

    public int getMsgStatus() {
        return MsgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        MsgStatus = msgStatus;
    }

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

    @Override
    public String toString() {
        return "OperateMsgModel{" +
                "Msgtype=" + Msgtype +
                ", Msgid='" + Msgid + '\'' +
                ", Recevid='" + Recevid + '\'' +
                ", Senderid='" + Senderid + '\'' +
                ", MsgContent='" + MsgContent + '\'' +
                ", Classid='" + Classid + '\'' +
                ", SendTime='" + SendTime + '\'' +
                ", IsRead=" + IsRead +
                ", IsDo=" + IsDo +
                ", MsgStatus=" + MsgStatus +
                ", SenderPhoto='" + SenderPhoto + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
