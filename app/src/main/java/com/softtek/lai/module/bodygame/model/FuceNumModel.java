/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame.model;

/**
 * Created by lareina.qiao on 3/29/2016.
 */
public class FuceNumModel {

    private String count;

    @Override
    public String toString() {
        return "FuceNumModel{" +
                "count='" + count + '\'' +
                '}';
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
