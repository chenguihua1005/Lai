package com.softtek.lai.module.healthchart.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class HealthUpArmGirthModel {
    private String firstrecordtime;
    private List<UpArmGirthlistModel> UpArmGirthlist;

    @Override
    public String toString() {
        return "HealthUpArmGirthModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", UpArmGirthlist=" + UpArmGirthlist +
                '}';
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }

    public List<UpArmGirthlistModel> getUpArmGirthlist() {
        return UpArmGirthlist;
    }

    public void setUpArmGirthlist(List<UpArmGirthlistModel> upArmGirthlist) {
        UpArmGirthlist = upArmGirthlist;
    }
}
