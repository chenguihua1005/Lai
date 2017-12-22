package com.softtek.lai.module.home.model;

/**
 * Created by jerry.guan on 10/28/2016.
 */

public class Version {

    /**
     * AppVisionId : 1020
     * AppName : 176B381904E6E56E
     * AppVisionNum : 2.3.1
     * AppVisionCode : 23100000
     * AppVisionLog : 修复BUG
     * UpdateTime : 2017-08-21T16:10:55.343
     * AppFileUrl : https://www.5ilai.cn/UpFiles/apk/2.3.1_231_jiagu_sign.apk
     * IsImperious : false
     */

    private int AppVisionId;
    private String AppName;
    private String AppVisionNum;
    private int AppVisionCode;
    private String AppVisionLog;
    private String UpdateTime;
    private String AppFileUrl;
    private boolean IsImperious;

    public int getAppVisionId() {
        return AppVisionId;
    }

    public void setAppVisionId(int AppVisionId) {
        this.AppVisionId = AppVisionId;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String AppName) {
        this.AppName = AppName;
    }

    public String getAppVisionNum() {
        return AppVisionNum;
    }

    public void setAppVisionNum(String AppVisionNum) {
        this.AppVisionNum = AppVisionNum;
    }

    public int getAppVisionCode() {
        return AppVisionCode;
    }

    public void setAppVisionCode(int AppVisionCode) {
        this.AppVisionCode = AppVisionCode;
    }

    public String getAppVisionLog() {
        return AppVisionLog;
    }

    public void setAppVisionLog(String AppVisionLog) {
        this.AppVisionLog = AppVisionLog;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getAppFileUrl() {
        return AppFileUrl;
    }

    public void setAppFileUrl(String AppFileUrl) {
        this.AppFileUrl = AppFileUrl;
    }

    public boolean isIsImperious() {
        return IsImperious;
    }

    public void setIsImperious(boolean IsImperious) {
        this.IsImperious = IsImperious;
    }
}
