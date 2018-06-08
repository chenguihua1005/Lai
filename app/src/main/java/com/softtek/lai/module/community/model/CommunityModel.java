package com.softtek.lai.module.community.model;

/**
 * Created by John on 2016/4/17.
 */
public class CommunityModel {

    private long Accountid;
    private String Content;
    private String Photos;


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public long getAccountid() {
        return Accountid;
    }

    public void setAccountid(long accountid) {
        Accountid = accountid;
    }

    public String getPhotos() {
        return Photos;
    }

    public void setPhotos(String photos) {
        Photos = photos;
    }
}
