package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by lareina.qiao on 3/10/2017.
 */

public class MonographicListModel {
    private List<TopicListModel> ArticleTopicList;//专题列表
    private int TotalPage;//总页数

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int totalPage) {
        TotalPage = totalPage;
    }

    public List<TopicListModel> getArticleTopicList() {
        return ArticleTopicList;
    }

    public void setArticleTopicList(List<TopicListModel> articleTopicList) {
        ArticleTopicList = articleTopicList;
    }
}
