package com.softtek.lai.module.historydate.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/20/2016.
 * 接受服务器请求的历史数据model
 */
public class HistoryDataModel {

    private String TotalPage;
    private List<HistoryData> HistoryList;

    public String getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<HistoryData> getHistoryList() {
        return HistoryList;
    }

    public void setHistoryList(List<HistoryData> historyList) {
        HistoryList = historyList;
    }
}
