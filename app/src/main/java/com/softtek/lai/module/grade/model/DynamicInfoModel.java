/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class DynamicInfoModel {

    private String DyInfoTitle;
    private String DyContent;
    private String CreateDate;
    private String Photo;
    private String DyType;

    public String getDyInfoTitle() {
        return DyInfoTitle;
    }

    public void setDyInfoTitle(String dyInfoTitle) {
        DyInfoTitle = dyInfoTitle;
    }

    public String getDyContent() {
        return DyContent;
    }

    public void setDyContent(String dyContent) {
        DyContent = dyContent;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getDyType() {
        return DyType;
    }

    public void setDyType(String dyType) {
        DyType = dyType;
    }

    @Override
    public String toString() {
        return "DynamicInfoModel{" +
                "DyInfoTitle='" + DyInfoTitle + '\'' +
                ", DyContent='" + DyContent + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", Photo='" + Photo + '\'' +
                ", DyType='" + DyType + '\'' +
                '}';
    }
}
