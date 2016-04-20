package com.softtek.lai.module.bodygamest.model;

import java.util.List;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public class DownPhotoModel {
    private String UserName;
    private String Photo;
    private String Banner;
    private String TotalPage;
    private List<LogListModel> LogList;

    @Override
    public String toString() {
        return "DownPhotoModel{" +
                "UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Banner='" + Banner + '\'' +
                ", TotalPage='" + TotalPage + '\'' +
                ", LogList=" + LogList +
                '}';
    }

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
        return TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<LogListModel> getLogList() {
        return LogList;
    }

    public void setLogList(List<LogListModel> logList) {
        LogList = logList;
    }
}
