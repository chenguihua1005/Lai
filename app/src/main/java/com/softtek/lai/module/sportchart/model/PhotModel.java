/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sportchart.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class PhotModel {
    private String path;

    @Override
    public String toString() {
        return "PhotModel{" +
                "path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
