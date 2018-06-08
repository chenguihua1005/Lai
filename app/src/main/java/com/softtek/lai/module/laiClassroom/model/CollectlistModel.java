package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public class CollectlistModel {
    private String ArticleId;//内容Id
    private int MediaType;//内容类型（1图文， 2视频）
    private String Title;//标题
    private int IsMultiPic;//是否为多图文  1：是 0：否
    private List<String> ArticImg;
    private String ArticUrl;//为视频时videourl
    private String VideoTime;//视频时间
    private int Clicks;
    private String TopicId;//专题Id
    private String Topic;//专题名
    private String CreateDate;//发布时间


    public CollectlistModel(String articleId, int mediaType, String title, int isMultiPic, List<String> articImg, String articUrl, String videoTime, int clicks, String topicId, String topic, String createDate) {
        ArticleId = articleId;
        MediaType = mediaType;
        Title = title;
        IsMultiPic = isMultiPic;
        ArticImg = articImg;
        ArticUrl = articUrl;
        VideoTime = videoTime;
        Clicks = clicks;
        TopicId = topicId;
        Topic = topic;
        CreateDate = createDate;
    }

    public int getIsMultiPic() {
        return IsMultiPic;
    }

    public void setIsMultiPic(int isMultiPic) {
        IsMultiPic = isMultiPic;
    }

    public String getArticleId() {
        return ArticleId;
    }

    public void setArticleId(String articleId) {
        ArticleId = articleId;
    }

    public int getMediaType() {
        return MediaType;
    }

    public void setMediaType(int mediaType) {
        MediaType = mediaType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<String> getArticImg() {
        return ArticImg;
    }

    public void setArticImg(List<String> articImg) {
        ArticImg = articImg;
    }

    public String getArticUrl() {
        return ArticUrl;
    }

    public void setArticUrl(String articUrl) {
        ArticUrl = articUrl;
    }

    public String getVideoTime() {
        return VideoTime;
    }

    public void setVideoTime(String videoTime) {
        VideoTime = videoTime;
    }

    public int getClicks() {
        return Clicks;
    }

    public void setClicks(int clicks) {
        Clicks = clicks;
    }

    public String getTopicId() {
        return TopicId;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }
}
