package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by jerry.guan on 11/18/2016.
 * 小区模型
 */

public class SmallRegion {

    private int RegionalId;
    private String RegionalName;
    private List<City> RegionalCityList;

    public int getRegionalId() {
        return RegionalId;
    }

    public void setRegionalId(int regionalId) {
        RegionalId = regionalId;
    }

    public String getRegionalName() {
        return RegionalName;
    }

    public void setRegionalName(String regionalName) {
        RegionalName = regionalName;
    }

    public List<City> getRegionalCityList() {
        return RegionalCityList;
    }

    public void setRegionalCityList(List<City> regionalCityList) {
        RegionalCityList = regionalCityList;
    }
}
