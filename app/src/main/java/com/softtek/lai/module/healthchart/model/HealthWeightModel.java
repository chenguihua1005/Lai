package com.softtek.lai.module.healthchart.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/22/2016.
 */
public class HealthWeightModel {
    private String firstrecordtime;
    private List<WeightlistModel> weightlist;

    @Override
    public String toString() {
        return "HealthWeightModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", weightlist=" + weightlist +
                '}';
    }

    public List<WeightlistModel> getweightlist() {
        return weightlist;
    }

    public void setweightlist(List<WeightlistModel> weightlist) {
        weightlist = weightlist;
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }
}
