package com.softtek.lai.module.community.model;

import java.util.List;

/**
 * Created by jerry.guan on 2/7/2017.
 */

public class DynamicModel {

    private long AccountId;
    private String UserName;
    private String UserPhoto;
    private String DynamicId;
    private String Content;
    private String CreateDate;
    private int IsTopic;
    private List<TopicList> TopicList;
    private int IsPraise;
    private int PraiseNum;
    private int IsFocus;
    private int CommendsNum;
    private List<String> usernameSet;
    private List<String> PhotoList;
    private List<String> ThumbnailPhotoList;
    private List<Comment> CommendsList;
    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public List<Comment> getCommendsList() {
        return CommendsList;
    }

    public void setCommendsList(List<Comment> commendsList) {
        CommendsList = commendsList;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long AccountId) {
        this.AccountId = AccountId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String UserPhoto) {
        this.UserPhoto = UserPhoto;
    }

    public String getDynamicId() {
        return DynamicId;
    }

    public void setDynamicId(String DynamicId) {
        this.DynamicId = DynamicId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public int getIsTopic() {
        return IsTopic;
    }

    public void setIsTopic(int IsTopic) {
        this.IsTopic = IsTopic;
    }

    public List<com.softtek.lai.module.community.model.TopicList> getTopicList() {
        return TopicList;
    }

    public void setTopicList(List<com.softtek.lai.module.community.model.TopicList> topicList) {
        TopicList = topicList;
    }

    public int getIsPraise() {
        return IsPraise;
    }

    public void setIsPraise(int IsPraise) {
        this.IsPraise = IsPraise;
    }

    public int getPraiseNum() {
        return PraiseNum;
    }

    public void setPraiseNum(int PraiseNum) {
        this.PraiseNum = PraiseNum;
    }

    public int getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(int IsFocus) {
        this.IsFocus = IsFocus;
    }

    public int getCommendsNum() {
        return CommendsNum;
    }

    public void setCommendsNum(int CommendsNum) {
        this.CommendsNum = CommendsNum;
    }

    public List<String> getUsernameSet() {
        return usernameSet;
    }

    public void setUsernameSet(List<String> usernameSet) {
        this.usernameSet = usernameSet;
    }

    public List<String> getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(List<String> PhotoList) {
        this.PhotoList = PhotoList;
    }

    public List<String> getThumbnailPhotoList() {
        return ThumbnailPhotoList;
    }

    public void setThumbnailPhotoList(List<String> ThumbnailPhotoList) {
        this.ThumbnailPhotoList = ThumbnailPhotoList;
    }

    @Override
    public String toString() {
        return "DynamicModel{" +
                "AccountId=" + AccountId +
                ", UserName='" + UserName + '\'' +
                ", UserPhoto='" + UserPhoto + '\'' +
                ", DynamicId='" + DynamicId + '\'' +
                ", Content='" + Content + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", IsTopic=" + IsTopic +
                ", TopicList"+TopicList+'\'' +
                ", IsPraise=" + IsPraise +
                ", PraiseNum=" + PraiseNum +
                ", IsFocus=" + IsFocus +
                ", CommendsNum=" + CommendsNum +
                ", usernameSet=" + usernameSet +
                ", PhotoList=" + PhotoList +
                ", ThumbnailPhotoList=" + ThumbnailPhotoList +
                ", CommendsList=" + CommendsList +
                '}';
    }
}
