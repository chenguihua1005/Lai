package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public class CollectModel {
    private List<CollectlistModel> ArticleList;
    private int TotalPage;

    public CollectModel(List<CollectlistModel> articleList, int totalPage) {
        ArticleList = articleList;
        TotalPage = totalPage;
    }

    public List<CollectlistModel> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<CollectlistModel> articleList) {
        ArticleList = articleList;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int totalPage) {
        TotalPage = totalPage;
    }
}
