package com.softtek.lai.module.bodygame3.conversation.model;

/**
 * Created by jessica.zhang on 2016/11/30.
 */

public class FriendModel {
//"ApplyId": "8e33be15-cc6e-48dc-8822-60aaa60de539",  --申请记录ID
//        "UserId": 48,
//        "UserName": "j季军",
//        "Role": 0,  -- 0：发送者(显示同意和拒绝按钮)， 1：接收者（显示对方的处理状态）
//        "HxGroupId": null,   --群组环信ID
//        "HxAccountId": "6c3577e47e0363df0b9aef37aaec907f", -- 好友环信ID
//        "SendTime": "2016-11-28 09:28:43",
//        "AcceptTime": "",
//        "Status": 0,  --0:未处理 1：已同意，-1：已拒绝
//        "Comment": null  ---备注

    private String ApplyId;//申请记录ID
    private int UserId;
    private String UserName;
    private int Role;//发送者(显示同意和拒绝按钮)， 1：接收者（显示对方的处理状态）
    private String HxGroupId;//群组环信ID
    private String HxAccountId;//好友环信ID
    private String SendTime;
    private String AcceptTime;
    private int Status;//0:未处理 1：已同意，-1：已拒绝
    private String Comment;//备注

    public FriendModel(String applyId, int userId, String userName, int role, String hxGroupId, String hxAccountId, String sendTime, String acceptTime, int status, String comment) {
        ApplyId = applyId;
        UserId = userId;
        UserName = userName;
        Role = role;
        HxGroupId = hxGroupId;
        HxAccountId = hxAccountId;
        SendTime = sendTime;
        AcceptTime = acceptTime;
        Status = status;
        Comment = comment;
    }

    public String getApplyId() {
        return ApplyId;
    }

    public void setApplyId(String applyId) {
        ApplyId = applyId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
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
