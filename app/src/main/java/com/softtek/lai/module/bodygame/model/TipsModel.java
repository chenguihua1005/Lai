package com.softtek.lai.module.bodygame.model;

/**
 * Created by lareina.qiao on 4/5/2016.
 */
public class TipsModel {
    private String Img_Id;
    private String Img_Type;
    private String Img_Title;

    @Override
    public String toString() {
        return "TipsModel{" +
                "Img_Id='" + Img_Id + '\'' +
                ", Img_Type='" + Img_Type + '\'' +
                ", Img_Title='" + Img_Title + '\'' +
                '}';
    }

    public String getImg_Id() {
        return Img_Id;
    }

    public void setImg_Id(String img_Id) {
        Img_Id = img_Id;
    }

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
}
