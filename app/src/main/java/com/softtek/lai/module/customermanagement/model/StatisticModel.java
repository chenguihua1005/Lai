package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 11/21/2017.
 */

public class StatisticModel {
    private String date;
    private String track;

    public StatisticModel(String date, String track) {
        this.date = date;
        this.track = track;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
