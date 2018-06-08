package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class TuijianModel {
//"VideoId": "19",
//        "VideoType": "营养课程",
//        "Photo": null,			————视频图片
//        "Title": "8",
//                "VideoUrl": "456.img",
//                "ClickCount": 8,

     private String VideoId;
    private String VideoType;
    private String Photo;//视频图片
    private String Title;
    private String VideoUrl;
    private int ClickCount;

    public TuijianModel(String videoId, String videoType, String photo, String title, String videoUrl, int clickCount) {
        VideoId = videoId;
        VideoType = videoType;
        Photo = photo;
        Title = title;
        VideoUrl = videoUrl;
        ClickCount = clickCount;
    }

    public int getClickCount() {
        return ClickCount;
    }

    public void setClickCount(int clickCount) {
        ClickCount = clickCount;
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
