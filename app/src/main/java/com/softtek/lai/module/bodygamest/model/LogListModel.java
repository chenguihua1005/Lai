package com.softtek.lai.module.bodygamest.model;

/**
 * Created by lareina.qiao on 4/19/2016.
 */
public class LogListModel {
    private String ImgUrl;
    private String CreateDate;

    public LogListModel(String imgUrl, String createDate) {

    }

    public LogListModel() {

    }

    @Override
    public String toString() {
        return "LogListModel{" +
                "ImgUrl='" + ImgUrl + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                '}';
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }
}
