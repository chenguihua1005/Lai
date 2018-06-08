/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class PhotosModel {
    private String Img;

    @Override
    public String toString() {
        return "PhotModel{" +
                "Img='" + Img + '\'' +
                '}';
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }
}
