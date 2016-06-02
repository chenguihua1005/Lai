/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class CheckClassEvent {
    private boolean b;

    @Override
    public String toString() {
        return "CheckClassEvent{" +
                "b=" + b +
                '}';
    }

    public CheckClassEvent(boolean b) {
        this.b = b;
    }

    public boolean isB() {

        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
}
