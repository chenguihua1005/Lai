package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class DynamicInfo {

    private String DyInfoTitle;
    private String DyContent;
    private String CreateDate;

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

    @Override
    public String toString() {
        return "DynamicInfo{" +
                "DyInfoTitle='" + DyInfoTitle + '\'' +
                ", DyContent='" + DyContent + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                '}';
    }
}
