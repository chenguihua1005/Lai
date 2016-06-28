package com.softtek.lai.module.pastreview.model;

import java.util.List;

/**
 * Created by jerry.guan on 6/28/2016.
 */
public class StoryList {
    private String PageCount;
    private List<StoryModel> LogList;

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }

    public List<StoryModel> getLogList() {
        return LogList;
    }

    public void setLogList(List<StoryModel> logList) {
        LogList = logList;
    }
}
