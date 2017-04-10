package com.softtek.lai.module.healthchart.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class HealthdoLegGirthModel {
    private String firstrecordtime;
    private List<DoLegGirthlistModel> DoLegGirthlist;

    @Override
    public String toString() {
        return "HealthdoLegGirthModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", DoLegGirthlist=" + DoLegGirthlist +
                '}';
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }

    public List<DoLegGirthlistModel> getDoLegGirthlist() {
        return DoLegGirthlist;
    }

    public void setDoLegGirthlist(List<DoLegGirthlistModel> doLegGirthlist) {
        DoLegGirthlist = doLegGirthlist;
    }
}
