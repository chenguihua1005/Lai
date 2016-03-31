/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class Phot {
    private String Img;

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    @Override
    public String toString() {
        return "Phot{" +
                "Img='" + Img + '\'' +
                '}';
    }
}
