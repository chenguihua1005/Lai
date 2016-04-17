package com.softtek.lai.module.community.model;

/**
 * Created by John on 2016/4/17.
 */
public class CommunityModel {

    private long AccountId;
    private String Title;
    private String Content;
    private int Htype;
    private String photoes;

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
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

    public int getHtype() {
        return Htype;
    }

    public void setHtype(int htype) {
        Htype = htype;
    }

    public String getPhotoes() {
        return photoes;
    }

    public void setPhotoes(String photoes) {
        this.photoes = photoes;
    }
}
