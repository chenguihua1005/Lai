/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class AssistantApplyInfo implements Serializable {

    private long ApplyId;     //申请记录id
    private String AccountId;        //顾问ID
    private String ClassId;     //班级Id
    private String UserName;     //助教名字
    private String Photo;        //头像路径
    private String Comments;        //申请详情

    public long getApplyId() {
        return ApplyId;
    }

    public void setApplyId(long applyId) {
        ApplyId = applyId;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    @Override
    public String toString() {
        return "AssistantApplyInfo{" +
                "ApplyId='" + ApplyId + '\'' +
                ", AccountId='" + AccountId + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Comments='" + Comments + '\'' +
                '}';
    }
}
