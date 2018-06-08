package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class PkNoticeModel {
    private String PKId;
    private String Photo;
    private String UserName;
    private String Chip;
    private int Msgtype;
    private String Msgid;
    private String MsgContent;
    private String SendTime;
    private int IsRead;

    private Boolean Isselect;

    public String getMsgid() {
        return Msgid;
    }

    public void setMsgid(String msgid) {
        Msgid = msgid;
    }

    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String msgContent) {
        MsgContent = msgContent;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public Boolean getIsselect() {
        return Isselect;
    }

    public void setIsselect(Boolean isselect) {
        Isselect = isselect;
    }


    public String getPKId() {
        return PKId;
    }

    public void setPKId(String PKId) {
        this.PKId = PKId;
    }

    public int getMsgtype() {
        return Msgtype;
    }

    public void setMsgtype(int msgtype) {
        Msgtype = msgtype;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getChip() {
        return Chip;
    }

    public void setChip(String chip) {
        Chip = chip;
    }

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }
}
