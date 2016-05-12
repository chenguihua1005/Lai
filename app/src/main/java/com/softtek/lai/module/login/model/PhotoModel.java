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

    private String Img;

    @Override
    public String toString() {
        return "PhotoModel{" +
                "Img='" + Img + '\'' +
                '}';
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}
