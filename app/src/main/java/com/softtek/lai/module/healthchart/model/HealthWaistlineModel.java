package com.softtek.lai.module.healthchart.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class HealthWaistlineModel {
    private String firstrecordtime;
    private List<WaistlinelistModel> Waistlinelist;

    @Override
    public String toString() {
        return "HealthWaistlineModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", Waistlinelist=" + Waistlinelist +
                '}';
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }

    public List<WaistlinelistModel> getWaistlinelist() {
        return Waistlinelist;
    }

    public void setWaistlinelist(List<WaistlinelistModel> waistlinelist) {
        Waistlinelist = waistlinelist;
    }
}
