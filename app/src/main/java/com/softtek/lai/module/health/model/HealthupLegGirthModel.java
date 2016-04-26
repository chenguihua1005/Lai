package com.softtek.lai.module.health.model;

import java.util.List;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class HealthupLegGirthModel {
    private String firstrecordtime;
    private List<UpLegGirthlistModel> UpLegGirthlist;

    @Override
    public String toString() {
        return "HealthupLegGirthModel{" +
                "firstrecordtime='" + firstrecordtime + '\'' +
                ", UpLegGirthlist=" + UpLegGirthlist +
                '}';
    }

    public String getFirstrecordtime() {
        return firstrecordtime;
    }

    public void setFirstrecordtime(String firstrecordtime) {
        this.firstrecordtime = firstrecordtime;
    }

    public List<UpLegGirthlistModel> getUpLegGirthlist() {
        return UpLegGirthlist;
    }

    public void setUpLegGirthlist(List<UpLegGirthlistModel> upLegGirthlist) {
        UpLegGirthlist = upLegGirthlist;
    }
}
