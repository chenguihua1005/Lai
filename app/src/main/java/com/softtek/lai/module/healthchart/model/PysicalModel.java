package com.softtek.lai.module.healthchart.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/19/2016.
 */
public class PysicalModel {
    private String firstrecordtime;
    private List<PysicallistModel> Pysicallist;

    @Override
    public String toString() {
        return "PysicalModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", Pysicallist=" + Pysicallist +
                '}';
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }

    public List<PysicallistModel> getPysicallist() {
        return Pysicallist;
    }

    public void setPysicallist(List<PysicallistModel> pysicallist) {
        Pysicallist = pysicallist;
    }
}
