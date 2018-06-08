/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-16
 */

package com.softtek.lai.module.login.model;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class IdentifyModel {

    private String identify;

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    @Override
    public String toString() {
        return "IdentifyModel{" +
                "identify='" + identify + '\'' +
                '}';
    }
}
