/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame.model;

import java.io.Serializable;

/**
 * Created by lareina.qiao on 3/17/2016.
 */
public class TiGuanSai implements Serializable {

    private String Img_Type;
    private String Img_Title;
    private String Img_Addr;
    private String UserRole;

    @Override
    public String toString() {
        return "TiGuanSai{" +
                "Img_Type='" + Img_Type + '\'' +
                ", Img_Title='" + Img_Title + '\'' +
                ", Img_Addr='" + Img_Addr + '\'' +
                ", Img_Content='" + Img_Content + '\'' +
                ", UserRole='" + UserRole + '\'' +
                '}';
    }

    private String Img_Content;

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public String getImg_Content() {
        return Img_Content;
    }

    public void setImg_Content(String img_Content) {
        Img_Content = img_Content;
    }

    public String getImg_Addr() {
        return Img_Addr;
    }

    public void setImg_Addr(String img_Addr) {
        Img_Addr = img_Addr;
    }

    public String getImg_Title() {
        return Img_Title;
    }

    public void setImg_Title(String img_Title) {
        Img_Title = img_Title;
    }

    public String getImg_Type() {
        return Img_Type;
    }

    public void setImg_Type(String img_Type) {
        Img_Type = img_Type;
    }


}
