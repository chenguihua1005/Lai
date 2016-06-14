package com.softtek.lai.module.laisportmine.model;

import java.io.Serializable;

/**
 * Created by lareina.qiao on 5/10/2016.
 */
public class RunTeamModel implements Serializable {
    private String RgName;
    private String IsHasMsg;
    private String IsHasAngelMsg;
    private String IsHasActMsg;
    private String IsHasChaMsg;
    private String RgNum;

    @Override
    public String toString() {
        return "RunTeamModel{" +
                "RgName='" + RgName + '\'' +
                ", IsHasMsg='" + IsHasMsg + '\'' +
                ", IsHasAngelMsg='" + IsHasAngelMsg + '\'' +
                ", IsHasActMsg='" + IsHasActMsg + '\'' +
                ", IsHasChaMsg='" + IsHasChaMsg + '\'' +
                ", RgNum='" + RgNum + '\'' +
                '}';
    }

    public String getRgName() {
        return RgName;
    }

    public void setRgName(String rgName) {
        RgName = rgName;
    }

    public String getIsHasMsg() {
        return IsHasMsg;
    }

    public void setIsHasMsg(String isHasMsg) {
        IsHasMsg = isHasMsg;
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

    public String getRgNum() {
        return RgNum;
    }

    public void setRgNum(String rgNum) {
        RgNum = rgNum;
    }

    public RunTeamModel() {
    }


}
