package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/10/2017.
 */

public class ArticalList {

    private int TotalPage;
    private List<Artical> ArticleList;

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int totalPage) {
        TotalPage = totalPage;
    }

    public List<Artical> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<Artical> articleList) {
        ArticleList = articleList;
    }
}
