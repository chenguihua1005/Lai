/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.model;

import java.io.Serializable;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class PhotoModel implements Serializable {

    public String Img;
    public String ThubImg;

    @Override
    public String toString() {
        return "PhotoModel{" +
                "Img='" + Img + '\'' +
                ", ThubImg='" + ThubImg + '\'' +
                '}';
    }
}
