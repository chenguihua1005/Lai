package com.softtek.lai.module.bodygame.model;

/**
 * Created by John on 2016/4/5.
 */
public class TipsDetailModel {
    private String Img_Id;
    private String Img_Type;
    private String Img_Title;
    private String Img_Content;

    public TipsDetailModel() {

    }

    @Override
    public String toString() {
        return "TipsDetailModel{" +
                "Img_Id='" + Img_Id + '\'' +
                ", Img_Type='" + Img_Type + '\'' +
                ", Img_Title='" + Img_Title + '\'' +
                ", Img_Content='" + Img_Content + '\'' +
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

    public String getImg_Content() {
        return Img_Content;
    }

    public void setImg_Content(String img_Content) {
        Img_Content = img_Content;
    }

    public TipsDetailModel(String img_Id, String img_Type, String img_Title, String img_Content) {
        Img_Id = img_Id;
        Img_Type = img_Type;
        Img_Title = img_Title;
        Img_Content = img_Content;
    }
}
