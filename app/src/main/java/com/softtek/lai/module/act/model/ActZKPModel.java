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
public class ActZKPModel implements Serializable {
    private String PageCount;
    private List<ActZKP1Model> AccDetiallist;

    @Override
    public String toString() {
        return "ActZKPModel{" +
                "PageCount='" + PageCount + '\'' +
                ", AccDetiallist=" + AccDetiallist +
                '}';
    }

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }

    public List<ActZKP1Model> getAccDetiallist() {
        return AccDetiallist;
    }

    public void setAccDetiallist(List<ActZKP1Model> accDetiallist) {
        AccDetiallist = accDetiallist;
    }
}
