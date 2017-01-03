package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 12/3/2016.
 */

public class VideoModel {
    private long VideoId;
    private String VideoType;
    private String Photo;//视频图片
    private String Title;
    private String VideoUrl;
    private int ClickCount;
    private String RoomLink;//跳转链接

    public VideoModel(long videoId, String videoType, String photo, String title, String videoUrl, int clickCount, String roomLink) {
        VideoId = videoId;
        VideoType = videoType;
        Photo = photo;
        Title = title;
        VideoUrl = videoUrl;
        ClickCount = clickCount;
        RoomLink = roomLink;
    }

    public long getVideoId() {
        return VideoId;
    }

    public void setVideoId(long videoId) {
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

    public int getClickCount() {
        return ClickCount;
    }

    public void setClickCount(int clickCount) {
        ClickCount = clickCount;
    }

    public String getRoomLink() {
        return RoomLink;
    }

    public void setRoomLink(String roomLink) {
        RoomLink = roomLink;
    }
}
