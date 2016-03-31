/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.model;

import java.io.Serializable;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public class HomeInfo implements Serializable {

    private String Img_Type;//放置位置

    private String Img_Title;//图片标题

    private String Img_Order;//显示顺序

    private String Img_Addr;//图片路径

    private String Img_Content;//详情

    public String getImg_Type() {
        return Img_Type;
    }

    public void setImg_Type(String img_Type) {
        Img_Type = img_Type;
    }

    public String getImg_Title() {
        return Img_Title;
    }

    public void setImg_Title(String img_Title) {
        Img_Title = img_Title;
    }

    public String getImg_Order() {
        return Img_Order;
    }

    public void setImg_Order(String img_Order) {
        Img_Order = img_Order;
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
        return "HomeInfo{" +
                "Img_Type='" + Img_Type + '\'' +
                ", Img_Title='" + Img_Title + '\'' +
                ", Img_Order='" + Img_Order + '\'' +
                ", Img_Addr='" + Img_Addr + '\'' +
                ", Img_Content='" + Img_Content + '\'' +
                '}';
    }
}
