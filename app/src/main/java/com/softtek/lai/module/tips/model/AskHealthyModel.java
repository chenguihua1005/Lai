package com.softtek.lai.module.tips.model;

/**
 * Created by jerry.guan on 4/27/2016.
 * 健康咨询
 */
public class AskHealthyModel {

    private String Tips_Id;
    private String Tips_Type;
    private String Tips_Title;
    private String Tips_Content;
    private String Tips_Addr;
    private String Tips_Link;

    public String getTips_Link() {
        return Tips_Link;
    }

    public void setTips_Link(String tips_Link) {
        Tips_Link = tips_Link;
    }

    public String getTips_Id() {
        return Tips_Id;
    }

    public void setTips_Id(String tips_Id) {
        Tips_Id = tips_Id;
    }

    public String getTips_Type() {
        return Tips_Type;
    }

    public void setTips_Type(String tips_Type) {
        Tips_Type = tips_Type;
    }

    public String getTips_Title() {
        return Tips_Title;
    }

    public void setTips_Title(String tips_Title) {
        Tips_Title = tips_Title;
    }

    public String getTips_Content() {
        return Tips_Content;
    }

    public void setTips_Content(String tips_Content) {
        Tips_Content = tips_Content;
    }

    public String getTips_Addr() {
        return Tips_Addr;
    }

    public void setTips_Addr(String tips_Addr) {
        Tips_Addr = tips_Addr;
    }
}
