package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/10/2016.
 */
public class RunTeamModel {
   private String RgName;
   private String IsHasMsg;

    @Override
    public String toString() {
        return "RunTeamModel{" +
                "RgName='" + RgName + '\'' +
                ", IsHasMsg='" + IsHasMsg + '\'' +
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
}
