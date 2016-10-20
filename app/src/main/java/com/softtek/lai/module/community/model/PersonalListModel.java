package com.softtek.lai.module.community.model;

/**
 * Created by jerry.guan on 10/9/2016.
 */
public class PersonalListModel {

    private String ID;
    private String Title;
    private String Content;
    private String CreateDate;
    private String minetype;
    private String imgCollection;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getMinetype() {
        return minetype;
    }

    public void setMinetype(String minetype) {
        this.minetype = minetype;
    }

    public String getImgCollection() {
        return imgCollection;
    }

    public void setImgCollection(String imgCollection) {
        this.imgCollection = imgCollection;
    }

    @Override
    public String toString() {
        return "PersonalListModel{" +
                "ID='" + ID + '\'' +
                ", Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", minetype='" + minetype + '\'' +
                ", imgCollection='" + imgCollection + '\'' +
                '}';
    }
}
