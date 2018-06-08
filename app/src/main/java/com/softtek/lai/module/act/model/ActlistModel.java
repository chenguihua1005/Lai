/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.act.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ActlistModel implements Serializable {

    private String AcStatus;  //--- 0：已结束   1:进行中   2:未开始
    private String ActId;
    private String ActTitle;
    private String Actimg;
    private String ActiveType;      //---1：团体    0：个人
    private String End;
    private String Start;

    @Override
    public String toString() {
        return "ActlistModel{" +
                "AcStatus='" + AcStatus + '\'' +
                ", ActId='" + ActId + '\'' +
                ", ActTitle='" + ActTitle + '\'' +
                ", Actimg='" + Actimg + '\'' +
                ", ActiveType='" + ActiveType + '\'' +
                ", End='" + End + '\'' +
                ", Start='" + Start + '\'' +
                '}';
    }

    public String getAcStatus() {
        return AcStatus;
    }

    public void setAcStatus(String acStatus) {
        AcStatus = acStatus;
    }

    public String getActId() {
        return ActId;
    }

    public void setActId(String actId) {
        ActId = actId;
    }

    public String getActTitle() {
        return ActTitle;
    }

    public void setActTitle(String actTitle) {
        ActTitle = actTitle;
    }

    public String getActimg() {
        return Actimg;
    }

    public void setActimg(String actimg) {
        Actimg = actimg;
    }

    public String getActiveType() {
        return ActiveType;
    }

    public void setActiveType(String activeType) {
        ActiveType = activeType;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }
}
