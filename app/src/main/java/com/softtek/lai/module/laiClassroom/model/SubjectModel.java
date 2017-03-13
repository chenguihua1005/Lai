package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by lareina.qiao on 3/13/2017.
 */

public class SubjectModel {
    private List<RecommendModel> RecommendTopicList;
    private List<ArticleTopicModel> ArticleTopicList;
    private int TotalPage;

    public List<RecommendModel> getRecommendTopicList() {
        return RecommendTopicList;
    }

    public void setRecommendTopicList(List<RecommendModel> recommendTopicList) {
        RecommendTopicList = recommendTopicList;
    }

    public List<ArticleTopicModel> getArticleTopicList() {
        return ArticleTopicList;
    }

    public void setArticleTopicList(List<ArticleTopicModel> articleTopicList) {
        ArticleTopicList = articleTopicList;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int totalPage) {
        TotalPage = totalPage;
    }
}
