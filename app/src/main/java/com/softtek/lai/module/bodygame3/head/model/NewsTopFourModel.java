package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by lareina.qiao on 12/2/2016.
 */
public class NewsTopFourModel {
    private String ImgId;
    private String ImgUrl;
    private String ThumbnailImgUrl;

    @Override
    public String toString() {
        return "NewsTopFourModel{" +
                "ImgId='" + ImgId + '\'' +
                ", ImgUrl='" + ImgUrl + '\'' +
                ", ThumbnailImgUrl='" + ThumbnailImgUrl + '\'' +
                '}';
    }

    public String getImgId() {
        return ImgId;
    }

    public void setImgId(String imgId) {
        ImgId = imgId;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getThumbnailImgUrl() {
        return ThumbnailImgUrl;
    }

    public void setThumbnailImgUrl(String thumbnailImgUrl) {
        ThumbnailImgUrl = thumbnailImgUrl;
    }
}
