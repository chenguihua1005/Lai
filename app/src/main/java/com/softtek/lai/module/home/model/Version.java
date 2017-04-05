package com.softtek.lai.module.home.model;

/**
 * Created by jerry.guan on 10/28/2016.
 */

public class Version {

    private String AppName;
    private int AppVisionCode;
    private int AppVisionId;
    private String AppVisionNum;
    private String UpdateTime;
    private String AppFileUrl;

    public String getAppFileUrl() {
        return AppFileUrl;
    }

    public void setAppFileUrl(String appFileUrl) {
        AppFileUrl = appFileUrl;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public int getAppVisionCode() {
        return AppVisionCode;
    }

    public void setAppVisionCode(int appVisionCode) {
        AppVisionCode = appVisionCode;
    }

    public int getAppVisionId() {
        return AppVisionId;
    }

    public void setAppVisionId(int appVisionId) {
        AppVisionId = appVisionId;
    }

    public String getAppVisionNum() {
        return AppVisionNum;
    }

    public void setAppVisionNum(String appVisionNum) {
        AppVisionNum = appVisionNum;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
