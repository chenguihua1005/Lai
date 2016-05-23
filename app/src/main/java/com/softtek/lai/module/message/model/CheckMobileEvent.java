/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class CheckMobileEvent {
    private int id;
    private boolean b;

    @Override
    public String toString() {
        return "CheckMobileEvent{" +
                "id=" + id +
                ", b=" + b +
                '}';
    }

    public int getId() {
        return id;
    }

    public CheckMobileEvent(int id, boolean b) {
        this.id = id;
        this.b = b;
    }

    public void setId(int id) {

        this.id = id;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
}
