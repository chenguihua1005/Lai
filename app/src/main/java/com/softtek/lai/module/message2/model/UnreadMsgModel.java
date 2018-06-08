/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class UnreadMsgModel {
    private String IsHasNoticeMsg;
    private String IsHasOperateMsg;
    private String IsHasMeasureMsg;
    private String IsHasAngelMsg;
    private String IsHasActMsg;
    private String IsHasChaMsg;
    private String IsHasFriendMsg;
    private String OperateMsg;
    private String NoticeMsg;
    private String MeasureMsg;
    private String AngleMsg;
    private String ActiveMsg;
    private String ChallMsg;
    private String FriendMsg;

    @Override
    public String toString() {
        return "UnreadMsgModel{" +
                "IsHasNoticeMsg='" + IsHasNoticeMsg + '\'' +
                ", IsHasOperateMsg='" + IsHasOperateMsg + '\'' +
                ", IsHasMeasureMsg='" + IsHasMeasureMsg + '\'' +
                ", IsHasAngelMsg='" + IsHasAngelMsg + '\'' +
                ", IsHasActMsg='" + IsHasActMsg + '\'' +
                ", IsHasChaMsg='" + IsHasChaMsg + '\'' +
                ", OperateMsg='" + OperateMsg + '\'' +
                ", NoticeMsg='" + NoticeMsg + '\'' +
                ", MeasureMsg='" + MeasureMsg + '\'' +
                ", AngleMsg='" + AngleMsg + '\'' +
                ", ActiveMsg='" + ActiveMsg + '\'' +
                ", ChallMsg='" + ChallMsg + '\'' +
                '}';
    }

    public String getIsHasFriendMsg() {
        return IsHasFriendMsg;
    }

    public void setIsHasFriendMsg(String isHasFriendMsg) {
        IsHasFriendMsg = isHasFriendMsg;
    }

    public String getFriendMsg() {
        return FriendMsg;
    }

    public void setFriendMsg(String friendMsg) {
        FriendMsg = friendMsg;
    }

    public String getIsHasNoticeMsg() {
        return IsHasNoticeMsg;
    }

    public void setIsHasNoticeMsg(String isHasNoticeMsg) {
        IsHasNoticeMsg = isHasNoticeMsg;
    }

    public String getIsHasOperateMsg() {
        return IsHasOperateMsg;
    }

    public void setIsHasOperateMsg(String isHasOperateMsg) {
        IsHasOperateMsg = isHasOperateMsg;
    }

    public String getIsHasMeasureMsg() {
        return IsHasMeasureMsg;
    }

    public void setIsHasMeasureMsg(String isHasMeasureMsg) {
        IsHasMeasureMsg = isHasMeasureMsg;
    }

    public String getIsHasAngelMsg() {
        return IsHasAngelMsg;
    }

    public void setIsHasAngelMsg(String isHasAngelMsg) {
        IsHasAngelMsg = isHasAngelMsg;
    }

    public String getIsHasActMsg() {
        return IsHasActMsg;
    }

    public void setIsHasActMsg(String isHasActMsg) {
        IsHasActMsg = isHasActMsg;
    }

    public String getIsHasChaMsg() {
        return IsHasChaMsg;
    }

    public void setIsHasChaMsg(String isHasChaMsg) {
        IsHasChaMsg = isHasChaMsg;
    }

    public String getOperateMsg() {
        return OperateMsg;
    }

    public void setOperateMsg(String operateMsg) {
        OperateMsg = operateMsg;
    }

    public String getNoticeMsg() {
        return NoticeMsg;
    }

    public void setNoticeMsg(String noticeMsg) {
        NoticeMsg = noticeMsg;
    }

    public String getMeasureMsg() {
        return MeasureMsg;
    }

    public void setMeasureMsg(String measureMsg) {
        MeasureMsg = measureMsg;
    }

    public String getAngleMsg() {
        return AngleMsg;
    }

    public void setAngleMsg(String angleMsg) {
        AngleMsg = angleMsg;
    }

    public String getActiveMsg() {
        return ActiveMsg;
    }

    public void setActiveMsg(String activeMsg) {
        ActiveMsg = activeMsg;
    }

    public String getChallMsg() {
        return ChallMsg;
    }

    public void setChallMsg(String challMsg) {
        ChallMsg = challMsg;
    }
}
