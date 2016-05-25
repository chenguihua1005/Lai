package com.softtek.lai.module.mygrades.model;

/**
 * Created by julie.zhu on 5/24/2016.
 */
public class RunGroupModel {
    private String RgName;
    private String IsHasMsg;
    private String IsHasAngelMsg;
    private String IsHasActMsg;
    private String IsHasChaMsg;

    public RunGroupModel(String rgName, String isHasMsg, String isHasAngelMsg, String isHasActMsg, String isHasChaMsg) {
        RgName = rgName;
        IsHasMsg = isHasMsg;
        IsHasAngelMsg = isHasAngelMsg;
        IsHasActMsg = isHasActMsg;
        IsHasChaMsg = isHasChaMsg;
    }

    @Override
    public String toString() {
        return "RunGroupModel{" +
                "RgName='" + RgName + '\'' +
                ", IsHasMsg='" + IsHasMsg + '\'' +
                ", IsHasAngelMsg='" + IsHasAngelMsg + '\'' +
                ", IsHasActMsg='" + IsHasActMsg + '\'' +
                ", IsHasChaMsg='" + IsHasChaMsg + '\'' +
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
}
