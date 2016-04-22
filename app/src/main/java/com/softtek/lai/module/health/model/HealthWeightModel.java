package com.softtek.lai.module.health.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/22/2016.
 */
public class HealthWeightModel {
    private String firstrecordtime;
    private List<weightlistModel> Pysicallist;

    @Override
    public String toString() {
        return "HealthWeightModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", Pysicallist=" + Pysicallist +
                '}';
    }

    public List<weightlistModel> getPysicallist() {
        return Pysicallist;
    }

    public void setPysicallist(List<weightlistModel> pysicallist) {
        Pysicallist = pysicallist;
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }
}
