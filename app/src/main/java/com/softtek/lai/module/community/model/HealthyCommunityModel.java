package com.softtek.lai.module.community.model;

/**
 * Created by jerry.guan on 4/11/2016.
 * 健康圈实体
 */
public class HealthyCommunityModel {

    private String ID;
    private String Title;
    private String Content;
    private String CreateDate;
    private String UserName;
    private String Photo;
    private String minetype;

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

    public String getMinetype() {
        return minetype;
    }

    public void setMinetype(String minetype) {
        this.minetype = minetype;
    }

    @Override
    public String toString() {
        return "HealthyCommunityModel{" +
                "ID='" + ID + '\'' +
                ", Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", minetype='" + minetype + '\'' +
                '}';
    }
}
