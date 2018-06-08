package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/10/2017.
 */

public class Artical {

    private String ArticleId;
    private int MediaType;
    private String Title;
    private int IsMultiPic;
    private String ArticUrl;
    private String VideoTime;
    private int Clicks;
    private String TopicId;
    private String Topic;
    private String CreateDate;
    private List<String> ArticImg;

    public String getArticleId() {
        return ArticleId;
    }

    public void setArticleId(String ArticleId) {
        this.ArticleId = ArticleId;
    }

    public int getMediaType() {
        return MediaType;
    }

    public void setMediaType(int MediaType) {
        this.MediaType = MediaType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getIsMultiPic() {
        return IsMultiPic;
    }

    public void setIsMultiPic(int IsMultiPic) {
        this.IsMultiPic = IsMultiPic;
    }

    public String getArticUrl() {
        return ArticUrl;
    }

    public void setArticUrl(String ArticUrl) {
        this.ArticUrl = ArticUrl;
    }

    public String getVideoTime() {
        return VideoTime;
    }

    public void setVideoTime(String VideoTime) {
        this.VideoTime = VideoTime;
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

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public List<String> getArticImg() {
        return ArticImg;
    }

    public void setArticImg(List<String> ArticImg) {
        this.ArticImg = ArticImg;
    }
}
