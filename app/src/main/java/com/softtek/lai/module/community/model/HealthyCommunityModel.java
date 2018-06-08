package com.softtek.lai.module.community.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 4/11/2016.
 * 健康圈实体
 */
public class HealthyCommunityModel {

    private String ID;
    private String Title;
    private String Content;
    private String CreateDate;
    private String minetype;
    private String imgCollection;
    private String UserName;
    private String Photo;
    private String IsPraise;
    private String PraiseNum;
    private String usernameSet;
    private int IsFocus;
    private String AccountId;


    public int getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(int isFocus) {
        IsFocus = isFocus;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getImgCollection() {
        return imgCollection;
    }

    public void setImgCollection(String imgCollection) {
        this.imgCollection = imgCollection;
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

    public String getUsernameSet() {
        return usernameSet;
    }

    public void setUsernameSet(String usernameSet) {
        this.usernameSet = usernameSet;
    }

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
                ", imgCollection='" + imgCollection + '\'' +
                ", IsPraise='" + IsPraise + '\'' +
                ", PraiseNum='" + PraiseNum + '\'' +
                ", usernameSet='" + usernameSet + '\'' +
                ", IsFocus=" + IsFocus +
                ", AccountId='" + AccountId + '\'' +
                '}';
    }
}
