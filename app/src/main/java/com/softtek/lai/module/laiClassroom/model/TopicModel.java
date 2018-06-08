package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by lareina.qiao on 3/14/2017.
 */

public class TopicModel {
    private List<String> TopicId;
    private List<String> TopicName;
    private List<String> TopicImg;
    private List<Integer> Clicks;

    public List<String> getTopicId() {
        return TopicId;
    }

    public void setTopicId(List<String> topicId) {
        TopicId = topicId;
    }

    public List<String> getTopicName() {
        return TopicName;
    }

    public void setTopicName(List<String> topicName) {
        TopicName = topicName;
    }

    public List<String> getTopicImg() {
        return TopicImg;
    }

    public void setTopicImg(List<String> topicImg) {
        TopicImg = topicImg;
    }

    public List<Integer> getClicks() {
        return Clicks;
    }

    public void setClicks(List<Integer> clicks) {
        Clicks = clicks;
    }
}
