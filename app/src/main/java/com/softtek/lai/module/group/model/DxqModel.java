/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.group.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class DxqModel implements Serializable {

    private String RegionId;
    private String RegionName;
    private String IsHeadOffice;

    @Override
    public String toString() {
        return "DxqModel{" +
                "RegionId='" + RegionId + '\'' +
                ", RegionName='" + RegionName + '\'' +
                ", IsHeadOffice='" + IsHeadOffice + '\'' +
                '}';
    }

    public String getIsHeadOffice() {
        return IsHeadOffice;
    }

    public void setIsHeadOffice(String isHeadOffice) {
        IsHeadOffice = isHeadOffice;
    }

    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }
}
