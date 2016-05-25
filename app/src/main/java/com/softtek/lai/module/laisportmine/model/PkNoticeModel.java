package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class PkNoticeModel {
    private String PKMsgId;
    private String PKId;
    private String MsgType;
    private String Comments;
    private String CreateTime;
    private String Photo;
    private String UserName;
    private String BPhoto;
    private String BUserName;
    private String Chip;

    @Override
    public String toString() {
        return "PkNoticeModel{" +
                "PKMsgId='" + PKMsgId + '\'' +
                ", PKId='" + PKId + '\'' +
                ", MsgType='" + MsgType + '\'' +
                ", Comments='" + Comments + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", Photo='" + Photo + '\'' +
                ", UserName='" + UserName + '\'' +
                ", BPhoto='" + BPhoto + '\'' +
                ", BUserName='" + BUserName + '\'' +
                ", Chip='" + Chip + '\'' +
                '}';
    }

    public String getPKMsgId() {
        return PKMsgId;
    }

    public void setPKMsgId(String PKMsgId) {
        this.PKMsgId = PKMsgId;
    }

    public String getPKId() {
        return PKId;
    }

    public void setPKId(String PKId) {
        this.PKId = PKId;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
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

    public String getBPhoto() {
        return BPhoto;
    }

    public void setBPhoto(String BPhoto) {
        this.BPhoto = BPhoto;
    }

    public String getBUserName() {
        return BUserName;
    }

    public void setBUserName(String BUserName) {
        this.BUserName = BUserName;
    }

    public String getChip() {
        return Chip;
    }

    public void setChip(String chip) {
        Chip = chip;
    }
}
