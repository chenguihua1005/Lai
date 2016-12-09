package com.softtek.lai.module.bodygame3.head.model;

import java.util.Arrays;
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
    private String IsHasTheme;//是否有主题，1有
    private String IsPraise;//是否点赞动态，1是
    private String PraiseNum;//点赞数
    private List<String> PraiseNameList;//点赞姓名数组
    private String IsFocus;//是否关注此学员
    private List<String> PhotoList;//照片列表
    private List<String> ThumbnailPhotoList;//照片列表缩略图
    private String CommendsNum;//评论数
    private List<PhotoWallComListModel>PhotoWallCommendsList;

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

    public String getIsHasTheme() {
        return IsHasTheme;
    }

    public void setIsHasTheme(String isHasTheme) {
        IsHasTheme = isHasTheme;
    }

    public String getIsPraise() {
        return IsPraise;
    }

    public void setIsPraise(String isPraise) {
        IsPraise = isPraise;
    }

    public String getPraiseNum() {
        return PraiseNum;
    }

    public void setPraiseNum(String praiseNum) {
        PraiseNum = praiseNum;
    }

    public List<String> getPraiseNameList() {
        return PraiseNameList;
    }

    public void setPraiseNameList(List<String> praiseNameList) {
        PraiseNameList = praiseNameList;
    }

    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
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

    public String getCommendsNum() {
        return CommendsNum;
    }

    public void setCommendsNum(String commendsNum) {
        CommendsNum = commendsNum;
    }

    public List<PhotoWallComListModel> getPhotoWallCommendsList() {
        return PhotoWallCommendsList;
    }

    public void setPhotoWallCommendsList(List<PhotoWallComListModel> photoWallCommendsList) {
        PhotoWallCommendsList = photoWallCommendsList;
    }
}
