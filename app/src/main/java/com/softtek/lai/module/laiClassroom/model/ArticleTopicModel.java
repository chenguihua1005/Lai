package com.softtek.lai.module.laiClassroom.model;

/**
 * Created by shelly.xu on 3/13/2017.
 */

public class ArticleTopicModel {
    private String TopicId;
    private String TopicName;
    private String TopicImg;
    private int Clicks;

    public String getTopicId() {
        return TopicId;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    public String getTopicImg() {
        return TopicImg;
    }

    public void setTopicImg(String topicImg) {
        TopicImg = topicImg;
    }

    public int getClicks() {
        return Clicks;
    }

    public void setClicks(int clicks) {
        Clicks = clicks;
    }
}
