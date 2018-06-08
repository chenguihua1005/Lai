package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class TimeAxisItemModel {
    private String CreatedTime;//记录时间
    private String Description;//事件描述

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "TimeAxisItemModel{" +
                "CreatedTime='" + CreatedTime + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
