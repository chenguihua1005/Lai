package com.softtek.lai.module.bodygamest.model;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public class DownPhotoModel {
    private String ImgUrl;
    private String CreateDate;

    @Override
    public String toString() {
        return "DownPhotoModel{" +
                "ImgUrl='" + ImgUrl + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                '}';
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public DownPhotoModel(String imgUrl, String createDate) {
        ImgUrl = imgUrl;
        CreateDate = createDate;
    }
}