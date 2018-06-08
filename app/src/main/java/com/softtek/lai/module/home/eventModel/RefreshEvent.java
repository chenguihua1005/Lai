/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.eventModel;

/**
 * Created by jerry.guan on 3/25/2016.
 */
public class RefreshEvent {

    public int flag;
    public boolean result = false;

    public RefreshEvent() {
    }

    public RefreshEvent(boolean result, int flag) {
        this.result = result;
        this.flag = flag;
    }
}
