/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame2pc.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class PhotModel {
    private String img;

    @Override
    public String toString() {
        return "PhotModel{" +
                "img='" + img + '\'' +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
