package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/13/2017.
 */

public class VideoDetailModel {

    private String Title;
    private String VideoImg;
    private String VideoUrl;
    private String VideoTime;
    private String VideoSize;
    private int Clicks;
    private int IsMark;
    private List<VideoList> VideoList;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getVideoImg() {
        return VideoImg;
    }

    public void setVideoImg(String VideoImg) {
        this.VideoImg = VideoImg;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String VideoUrl) {
        this.VideoUrl = VideoUrl;
    }

    public String getVideoTime() {
        return VideoTime;
    }

    public void setVideoTime(String VideoTime) {
        this.VideoTime = VideoTime;
    }

    public String getVideoSize() {
        return VideoSize;
    }

    public void setVideoSize(String VideoSize) {
        this.VideoSize = VideoSize;
    }

    public int getClicks() {
        return Clicks;
    }

    public void setClicks(int Clicks) {
        this.Clicks = Clicks;
    }

    public int getIsMark() {
        return IsMark;
    }

    public void setIsMark(int IsMark) {
        this.IsMark = IsMark;
    }

    public List<VideoList> getVideoList() {
        return VideoList;
    }

    public void setVideoList(List<VideoList> VideoList) {
        this.VideoList = VideoList;
    }

    public static class VideoList {

        private String ArticleId;
        private String Title;
        private String VideoImg;
        private int Clicks;
        private String TopicId;
        private String Topic;
        private String VideoUrl;

        public String getVideoUrl() {
            return VideoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            VideoUrl = videoUrl;
        }

        public String getArticleId() {
            return ArticleId;
        }

        public void setArticleId(String ArticleId) {
            this.ArticleId = ArticleId;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getVideoImg() {
            return VideoImg;
        }

        public void setVideoImg(String VideoImg) {
            this.VideoImg = VideoImg;
        }

        public int getClicks() {
            return Clicks;
        }

        public void setClicks(int Clicks) {
            this.Clicks = Clicks;
        }

        public String getTopicId() {
            return TopicId;
        }

        public void setTopicId(String TopicId) {
            this.TopicId = TopicId;
        }

        public String getTopic() {
            return Topic;
        }

        public void setTopic(String Topic) {
            this.Topic = Topic;
        }
    }
}
