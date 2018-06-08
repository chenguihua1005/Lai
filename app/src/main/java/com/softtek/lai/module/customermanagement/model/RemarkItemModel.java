package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 11/21/2017.
 */

public class RemarkItemModel {
    private String UserName;
    private String Photo;
    private String CreatedTime;
    private String Description;

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

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "RemarkItemModel{" +
                "UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", CreatedTime='" + CreatedTime + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
