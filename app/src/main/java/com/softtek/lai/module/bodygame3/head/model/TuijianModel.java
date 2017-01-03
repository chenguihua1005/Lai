package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class TuijianModel {

     private String VideoId;
    private String VideoType;
    private String Photo;//视频图片
    private String Title;
    private String VideoUrl;

    public TuijianModel(String videoId, String videoType, String photo, String title, String videoUrl) {
        VideoId = videoId;
        VideoType = videoType;
        Photo = photo;
        Title = title;
        VideoUrl = videoUrl;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getVideoType() {
        return VideoType;
    }

    public void setVideoType(String videoType) {
        VideoType = videoType;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "TuijianModel{" +
                "VideoId='" + VideoId + '\'' +
                ", VideoType='" + VideoType + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Title='" + Title + '\'' +
                ", VideoUrl='" + VideoUrl + '\'' +
                '}';
    }
}
