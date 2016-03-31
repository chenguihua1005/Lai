/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class PhotoInfo {

    private String Img_Title;

    private String Img_Addr;

    private String Img_Content;

    public String getImg_Title() {
        return Img_Title;
    }

    public void setImg_Title(String img_Title) {
        Img_Title = img_Title;
    }

    public String getImg_Addr() {
        return Img_Addr;
    }

    public void setImg_Addr(String img_Addr) {
        Img_Addr = img_Addr;
    }

    public String getImg_Content() {
        return Img_Content;
    }

    public void setImg_Content(String img_Content) {
        Img_Content = img_Content;
    }

    @Override
    public String toString() {
        return "PhotoInfo{" +
                "Img_Title='" + Img_Title + '\'' +
                ", Img_Addr='" + Img_Addr + '\'' +
                ", Img_Content='" + Img_Content + '\'' +
                '}';
    }
}
