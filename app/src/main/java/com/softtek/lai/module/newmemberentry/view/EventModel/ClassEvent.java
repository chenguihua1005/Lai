/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.EventModel;

import com.softtek.lai.module.newmemberentry.view.model.Pargrade;

import java.util.List;

/**
 * Created by julie.zhu on 3/23/2016.
 */
public class ClassEvent {
    private List<Pargrade> pargrades;

    public List<Pargrade> getPargrades() {
        return pargrades;
    }

    public ClassEvent(List<Pargrade> pargrades) {
        this.pargrades = pargrades;
    }

    public void setPargrades(List<Pargrade> pargrades) {
        this.pargrades = pargrades;
    }
}
