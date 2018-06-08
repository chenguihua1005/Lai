package com.softtek.lai.module.community.model;

/**
 * Created by jerry.guan on 2/7/2017.
 * 话题信息模型
 */

public class TopicInfo {


    private String TopicType;
    private String TopicName;
    private String TopicPhoto;
    private String TopicExplain;
    private int DynamicNum;
    private String TopciCover;

    public String getTopciCover() {
        return TopciCover;
    }

    public void setTopciCover(String topciCover) {
        TopciCover = topciCover;
    }

    public int getDynamicNum() {
        return DynamicNum;
    }

    public void setDynamicNum(int dynamicNum) {
        DynamicNum = dynamicNum;
    }

    public String getTopicType() {
        return TopicType;
    }

    public void setTopicType(String TopicType) {
        this.TopicType = TopicType;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String TopicName) {
        this.TopicName = TopicName;
    }

    public String getTopicPhoto() {
        return TopicPhoto;
    }

    public void setTopicPhoto(String TopicPhoto) {
        this.TopicPhoto = TopicPhoto;
    }

    public String getTopicExplain() {
        return TopicExplain;
    }

    public void setTopicExplain(String TopicExplain) {
        this.TopicExplain = TopicExplain;
    }

    @Override
    public String                                                     toString() {
        return "TopicInfo{" +
                "TopicType='" + TopicType + '\'' +
                ", TopicName='" + TopicName + '\'' +
                ", TopicPhoto='" + TopicPhoto + '\'' +
                ", TopicExplain='" + TopicExplain + '\'' +
                ", DynamicNum=" + DynamicNum +
                '}';
    }
}
