package com.softtek.lai.module.pastreview.model;

/**
 * Created by jerry.guan on 6/30/2016.
 */
public class LossStory {

    private String LossLogId;
    private String CreateDate;
    private String ImgUrl;
    private String LogContent;
    private String Priase;

    public String getLossLogId() {
        return LossLogId;
    }

    public void setLossLogId(String lossLogId) {
        LossLogId = lossLogId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getLogContent() {
        return LogContent;
    }

    public void setLogContent(String logContent) {
        LogContent = logContent;
    }

    public String getPriase() {
        return Priase;
    }

    public void setPriase(String priase) {
        Priase = priase;
    }
}
