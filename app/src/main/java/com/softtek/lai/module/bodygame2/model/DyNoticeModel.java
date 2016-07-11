package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class DyNoticeModel {
private String CreateDate;
private String DyContent;
private String DyType;
private String Photo;

    public DyNoticeModel(String createDate, String dyContent, String dyType, String photo) {
        CreateDate = createDate;
        DyContent = dyContent;
        DyType = dyType;
        Photo = photo;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getDyContent() {
        return DyContent;
    }

    public void setDyContent(String dyContent) {
        DyContent = dyContent;
    }

    public String getDyType() {
        return DyType;
    }

    public void setDyType(String dyType) {
        DyType = dyType;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
