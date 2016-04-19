package com.softtek.lai.module.lossweightstory.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/15/2016.
 */
public class LogList {

    private String UserName;
    private String Photo;
    private String Banner;
    private String TotalPage;
    private List<LossWeightStoryModel> LogList;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String banner) {
        Banner = banner;
    }

    public String getTotalPage() {
        return TotalPage==null||"".equals(TotalPage)?"0":TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<LossWeightStoryModel> getLogList() {
        return LogList;
    }

    public void setLogList(List<LossWeightStoryModel> logList) {
        LogList = logList;
    }

    @Override
    public String toString() {
        return "LogList{" +
                "UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Banner='" + Banner + '\'' +
                ", TotalPage='" + TotalPage + '\'' +
                ", LogList=" + LogList +
                '}';
    }
}
