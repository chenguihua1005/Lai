/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class RetDataModel implements Serializable {

    private String city;
    private String time;
    private String aqi;
    private String level;
    private String core;

    @Override
    public String toString() {
        return "RetDataModel{" +
                "city='" + city + '\'' +
                ", time='" + time + '\'' +
                ", aqi='" + aqi + '\'' +
                ", level='" + level + '\'' +
                ", core='" + core + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }
}
