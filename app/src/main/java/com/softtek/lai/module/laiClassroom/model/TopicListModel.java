package com.softtek.lai.module.laiClassroom.model;

/**
 * Created by lareina.qiao on 3/10/2017.
 */

public class TopicListModel {
    private String TopicId;//专题id
    private String TopicName;//专题名
    private String TopicImg;//专题主题图片
    private String Clicks;//点击热度

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

    public String getClicks() {
        return Clicks;
    }

    public void setClicks(String clicks) {
        Clicks = clicks;
    }
}