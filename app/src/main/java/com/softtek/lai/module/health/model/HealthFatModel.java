package com.softtek.lai.module.health.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class HealthFatModel {
    private String firstrecordtime;
    private List<FatlistModel> Fatlist;

    @Override
    public String toString() {
        return "HealthFatModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", Fatlist=" + Fatlist +
                '}';
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }

    public List<FatlistModel> getFatlist() {
        return Fatlist;
    }

    public void setFatlist(List<FatlistModel> fatlist) {
        Fatlist = fatlist;
    }
}
