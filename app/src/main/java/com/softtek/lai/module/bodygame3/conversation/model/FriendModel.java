package com.softtek.lai.module.bodygame3.conversation.model;

/**
 * Created by jessica.zhang on 2016/11/30.
 */

public class FriendModel {

    private String ApplyId;//申请记录ID
    private long UserId;
    private String UserName;
    private String Photo;//头像
    private String ClassName;//班级名称
    private int ClassRole;//班级角色：1：开班教练，2：组别教练， 3：组别助教 4：学员
    private int Role;//发送者(显示同意和拒绝按钮)， 1：接收者（显示对方的处理状态）
    private String HxGroupId;//群组环信ID
    private String HxAccountId;//好友环信ID
    private String SendTime;
    private String AcceptTime;
    private int Status;//0:未处理 1：已同意，-1：已拒绝
    private String Comment;//备注

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int classRole) {
        ClassRole = classRole;
    }

    public String getApplyId() {
        return ApplyId;
    }

    public void setApplyId(String applyId) {
        ApplyId = applyId;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public String getHxGroupId() {
        return HxGroupId;
    }

    public void setHxGroupId(String hxGroupId) {
        HxGroupId = hxGroupId;
    }

    public String getHxAccountId() {
        return HxAccountId;
    }

    public void setHxAccountId(String hxAccountId) {
        HxAccountId = hxAccountId;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
