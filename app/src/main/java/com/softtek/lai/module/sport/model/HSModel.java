/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HSModel implements Serializable {

    private String pageSize;
    private List<HistorySportModel> list;

    @Override
    public String toString() {
        return "HSModel{" +
                "pageSize='" + pageSize + '\'' +
                ", list=" + list +
                '}';
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<HistorySportModel> getList() {
        return list;
    }

    public void setList(List<HistorySportModel> list) {
        this.list = list;
    }
}
