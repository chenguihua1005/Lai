package com.softtek.lai.module.health.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class HealthCircrumModel {
    private String firstrecordtime;
    private List<CircumlistModel> Circumlist;

    @Override
    public String toString() {
        return "HealthCircrumModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", Circumlist=" + Circumlist +
                '}';
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }

    public List<CircumlistModel> getCircumlist() {
        return Circumlist;
    }

    public void setCircumlist(List<CircumlistModel> circumlist) {
        Circumlist = circumlist;
    }
}
