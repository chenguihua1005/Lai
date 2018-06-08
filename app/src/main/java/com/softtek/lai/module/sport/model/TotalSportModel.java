/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class TotalSportModel implements Serializable {

    private String TotalKilometer;
    private String TotalTime;
    private String count;

    @Override
    public String toString() {
        return "TotalSportModel{" +
                "TotalKilometer='" + TotalKilometer + '\'' +
                ", TotalTime='" + TotalTime + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public String getTotalKilometer() {
        return TotalKilometer;
    }

    public void setTotalKilometer(String totalKilometer) {
        TotalKilometer = totalKilometer;
    }

    public String getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(String totalTime) {
        TotalTime = totalTime;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
