package com.softtek.lai.module.bodygame3.photowall.model;

/**
 * Created by Terry on 2016/12/10.
 */
public class PublicDyModel {
    private long Accountid;
    private String Content;
    private String keywordId;
    private String ClassId;
    private String Photos;
    private String ThumbnailPhotos;

    @Override
    public String toString() {
        return "PublicDyModel{" +
                "Accountid=" + Accountid +
                ", Content='" + Content + '\'' +
                ", keywordId='" + keywordId + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", Photos='" + Photos + '\'' +
                '}';
    }

    public String getThumbnailPhotos() {
        return ThumbnailPhotos;
    }

    public void setThumbnailPhotos(String thumbnailPhotos) {
        ThumbnailPhotos = thumbnailPhotos;
    }

    public long getAccountid() {
        return Accountid;
    }

    public void setAccountid(long accountid) {
        Accountid = accountid;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(String keywordId) {
        this.keywordId = keywordId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getPhotos() {
        return Photos;
    }

    public void setPhotos(String photos) {
        Photos = photos;
    }
}
