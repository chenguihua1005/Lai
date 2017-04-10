package com.softtek.lai.module.healthchart.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class HealthHiplieModel {
    private String firstrecordtime;
    private List<HiplielistModel> Hiplielist;

    @Override
    public String toString() {
        return "HealthHiplieModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", Hiplielist=" + Hiplielist +
                '}';
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }

    public List<HiplielistModel> getHiplielist() {
        return Hiplielist;
    }

    public void setHiplielist(List<HiplielistModel> hiplielist) {
        Hiplielist = hiplielist;
    }
}
