/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.act.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ActivityModel implements Serializable {

    private String PageCount;
    private List<ActlistModel> Actlist;

    @Override
    public String toString() {
        return "ActivityModel{" +
                "PageCount='" + PageCount + '\'' +
                ", Actlist=" + Actlist +
                '}';
    }

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }

    public List<ActlistModel> getActlist() {
        return Actlist;
    }

    public void setActlist(List<ActlistModel> actlist) {
        Actlist = actlist;
    }
}
