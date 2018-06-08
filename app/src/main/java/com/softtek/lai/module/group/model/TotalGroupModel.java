/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.group.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class TotalGroupModel implements Serializable {

    private String RGId;
    private String RGName;
    private String IsHasSonRG;
    private String RGNum;

    @Override
    public String toString() {
        return "GroupModel{" +
                "RGId='" + RGId + '\'' +
                ", RGName='" + RGName + '\'' +
                ", IsHasSonRG='" + IsHasSonRG + '\'' +
                ", RGNum='" + RGNum + '\'' +
                '}';
    }

    public String getRGId() {
        return RGId;
    }

    public void setRGId(String RGId) {
        this.RGId = RGId;
    }

    public String getRGName() {
        return RGName;
    }

    public void setRGName(String RGName) {
        this.RGName = RGName;
    }

    public String getIsHasSonRG() {
        return IsHasSonRG;
    }

    public void setIsHasSonRG(String isHasSonRG) {
        IsHasSonRG = isHasSonRG;
    }

    public String getRGNum() {
        return RGNum;
    }

    public void setRGNum(String RGNum) {
        this.RGNum = RGNum;
    }
}
