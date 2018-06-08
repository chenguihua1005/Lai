package com.softtek.lai.module.community.model;

/**
 * Created by jerry.guan on 2/22/2017.
 */

public class TopicList {

    private String TopicType;
    private String TopicName;

    public String getTopicType() {
        return TopicType;
    }

    public void setTopicType(String topicType) {
        TopicType = topicType;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    @Override
    public String toString() {
        return "TopicList{" +
                "TopicType='" + TopicType + '\'' +
                ", TopicName='" + TopicName + '\'' +
                '}';
    }
}
