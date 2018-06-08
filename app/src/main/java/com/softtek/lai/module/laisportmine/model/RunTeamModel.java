package com.softtek.lai.module.laisportmine.model;

import java.io.Serializable;

/**
 * Created by lareina.qiao on 5/10/2016.
 */
public class RunTeamModel implements Serializable {
    private String RgName;
    private String RgPhoto;
    private String RgManager;
    private String RgNum;

    @Override
    public String toString() {
        return "RunTeamModel{" +
                "RgName='" + RgName + '\'' +
                ", RgPhoto='" + RgPhoto + '\'' +
                ", RgManager='" + RgManager + '\'' +
                ", RgNum='" + RgNum + '\'' +
                '}';
    }

    public RunTeamModel() {

    }

    public String getRgName() {
        return RgName;
    }

    public void setRgName(String rgName) {
        RgName = rgName;
    }

    public String getRgPhoto() {
        return RgPhoto;
    }

    public void setRgPhoto(String rgPhoto) {
        RgPhoto = rgPhoto;
    }

    public String getRgManager() {
        return RgManager;
    }

    public void setRgManager(String rgManager) {
        RgManager = rgManager;
    }

    public String getRgNum() {
        return RgNum;
    }

    public void setRgNum(String rgNum) {
        RgNum = rgNum;
    }
}
