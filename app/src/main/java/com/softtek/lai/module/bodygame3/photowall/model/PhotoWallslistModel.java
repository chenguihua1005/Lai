package com.softtek.lai.module.bodygame3.photowall.model;

import com.softtek.lai.module.community.model.TopicList;

import java.util.List;

/**
 * Created by lareina.qiao on 12/3/2016.
 */
public class PhotoWallslistModel {
    private String Accountid;//用户id
    private String UserName;//用户名
    private String UserPhoto;//用户头像
    private String UserThPhoto;//用户头像缩略图
    private String HealtId;//动态id
    private String Content;//内容
    private String Createdate;//发表日期
    private int IsHasTheme;//是否有主题，1有
    private String ThemeName;//主题名称
    private int IsPraise;//是否点赞动态，1是
    private int PraiseNum;//点赞数
    private List<String> PraiseNameList;//点赞姓名数组
    private int IsFocus;//是否关注此学员
    private List<String> PhotoList;//照片列表
    private List<String> ThumbnailPhotoList;//照片列表缩略图
    private int CommendsNum;//评论数
    private List<CommentModel> PhotoWallCommendsList;
    private int CurrWeek;//第几体管周
    private List<TopicList> TopicList;
    private boolean isOpen;

    public List<com.softtek.lai.module.community.model.TopicList> getTopicList() {
        return TopicList;
    }

    public void setTopicList(List<com.softtek.lai.module.community.model.TopicList> topicList) {
        TopicList = topicList;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getCurrWeek() {
        return CurrWeek;
    }

    public void setCurrWeek(int currWeek) {
        CurrWeek = currWeek;
    }

    public String getThemeName() {
        return ThemeName;
    }

    public void setThemeName(String themeName) {
        ThemeName = themeName;
    }

    public String getAccountid() {
        return Accountid;
    }

    public void setAccountid(String accountid) {
        Accountid = accountid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }

    public String getUserThPhoto() {
        return UserThPhoto;
    }

    public void setUserThPhoto(String userThPhoto) {
        UserThPhoto = userThPhoto;
    }

    public String getHealtId() {
        return HealtId;
    }

    public void setHealtId(String healtId) {
        HealtId = healtId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreatedate() {
        return Createdate;
    }

    public void setCreatedate(String createdate) {
        Createdate = createdate;
    }

    public int getIsHasTheme() {
        return IsHasTheme;
    }

    public void setIsHasTheme(int isHasTheme) {
        IsHasTheme = isHasTheme;
    }

    public int getIsPraise() {
        return IsPraise;
    }

    public void setIsPraise(int isPraise) {
        IsPraise = isPraise;
    }

    public int getPraiseNum() {
        return PraiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        PraiseNum = praiseNum;
    }

    public void setIsFocus(int isFocus) {
        IsFocus = isFocus;
    }

    public List<String> getPraiseNameList() {
        return PraiseNameList;
    }

    public void setPraiseNameList(List<String> praiseNameList) {
        PraiseNameList = praiseNameList;
    }

    public int getIsFocus() {
        return IsFocus;
    }

    public List<String> getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(List<String> photoList) {
        PhotoList = photoList;
    }

    public List<String> getThumbnailPhotoList() {
        return ThumbnailPhotoList;
    }

    public void setThumbnailPhotoList(List<String> thumbnailPhotoList) {
        ThumbnailPhotoList = thumbnailPhotoList;
    }

    public int getCommendsNum() {
        return CommendsNum;
    }

    public void setCommendsNum(int commendsNum) {
        CommendsNum = commendsNum;
    }

    public List<CommentModel> getPhotoWallCommendsList() {
        return PhotoWallCommendsList;
    }

    public void setPhotoWallCommendsList(List<CommentModel> photoWallCommendsList) {
        PhotoWallCommendsList = photoWallCommendsList;
    }

    @Override
    public String toString() {
        return "PhotoWallslistModel{" +
                "Accountid='" + Accountid + '\'' +
                ", UserName='" + UserName + '\'' +
                ", UserPhoto='" + UserPhoto + '\'' +
                ", UserThPhoto='" + UserThPhoto + '\'' +
                ", HealtId='" + HealtId + '\'' +
                ", Content='" + Content + '\'' +
                ", Createdate='" + Createdate + '\'' +
                ", IsHasTheme=" + IsHasTheme +
                ", IsPraise=" + IsPraise +
                ", PraiseNum=" + PraiseNum +
                ", PraiseNameList=" + PraiseNameList +
                ", IsFocus=" + IsFocus +
                ", PhotoList=" + PhotoList +
                ", ThumbnailPhotoList=" + ThumbnailPhotoList +
                ", CommendsNum=" + CommendsNum +
                ", PhotoWallCommendsList=" + PhotoWallCommendsList +
                '}';
    }
}
