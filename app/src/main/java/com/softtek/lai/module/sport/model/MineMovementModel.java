/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MineMovementModel implements Serializable {

    private String RgTime;

    @Override
    public String toString() {
        return "MineMovementModel{" +
                "RgTime='" + RgTime + '\'' +
                '}';
    }

    public String getRgTime() {
        return RgTime;
    }

    public void setRgTime(String rgTime) {
        RgTime = rgTime;
    }
}
