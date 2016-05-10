package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/10/2016.
 */
public class RunTeamModel {
    String RgName;

    @Override
    public String toString() {
        return "RunTeamModel{" +
                "RgName='" + RgName + '\'' +
                '}';
    }

    public String getRgName() {
        return RgName;
    }

    public void setRgName(String rgName) {
        RgName = rgName;
    }
}
