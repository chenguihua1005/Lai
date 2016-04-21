package com.softtek.lai.module.lossweightstory.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 2016/4/14.
 */
public class LossWeightStoryModel implements Parcelable{

    private String UserName;
    private String Photo;
    private String AcBanner;
    private String LossLogId;
    private String CreateDate;
    private String LogTitle;
    private String LogContent;
    private String Priase;
    private String imgCollectionFirst;
    private String imgCollection;
    private String isClicked;
    private String usernameSet;
    private String AfterWeight;

    public LossWeightStoryModel() {
    }

    protected LossWeightStoryModel(Parcel in) {
        UserName = in.readString();
        Photo = in.readString();
        AcBanner = in.readString();
        LossLogId = in.readString();
        CreateDate = in.readString();
        LogTitle = in.readString();
        LogContent = in.readString();
        Priase = in.readString();
        imgCollectionFirst = in.readString();
        imgCollection = in.readString();
        isClicked = in.readString();
        usernameSet = in.readString();
        AfterWeight = in.readString();
    }

    public static final Creator<LossWeightStoryModel> CREATOR = new Creator<LossWeightStoryModel>() {
        @Override
        public LossWeightStoryModel createFromParcel(Parcel in) {
            return new LossWeightStoryModel(in);
        }

        @Override
        public LossWeightStoryModel[] newArray(int size) {
            return new LossWeightStoryModel[size];
        }
    };

    public String getAfterWeight() {
        return AfterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        AfterWeight = afterWeight;
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

    public String getAcBanner() {
        return AcBanner;
    }

    public void setAcBanner(String acBanner) {
        AcBanner = acBanner;
    }

    public String getLossLogId() {
        return LossLogId;
    }

    public void setLossLogId(String lossLogId) {
        LossLogId = lossLogId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getLogTitle() {
        return LogTitle;
    }

    public void setLogTitle(String logTitle) {
        LogTitle = logTitle;
    }

    public String getLogContent() {
        return LogContent;
    }

    public void setLogContent(String logContent) {
        LogContent = logContent;
    }

    public String getPriase() {
        return Priase;
    }

    public void setPriase(String priase) {
        Priase = priase;
    }

    public String getImgCollectionFirst() {
        return imgCollectionFirst;
    }

    public void setImgCollectionFirst(String imgCollectionFirst) {
        this.imgCollectionFirst = imgCollectionFirst;
    }

    public String getImgCollection() {
        return imgCollection;
    }

    public void setImgCollection(String imgCollection) {
        this.imgCollection = imgCollection;
    }

    public String getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(String isClicked) {
        this.isClicked = isClicked;
    }

    public String getUsernameSet() {
        return usernameSet;
    }

    public void setUsernameSet(String usernameSet) {
        this.usernameSet = usernameSet;
    }

    @Override
    public String toString() {
        return "LossWeightStoryModel{" +
                "UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", AcBanner='" + AcBanner + '\'' +
                ", LossLogId='" + LossLogId + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", LogTitle='" + LogTitle + '\'' +
                ", LogContent='" + LogContent + '\'' +
                ", Priase='" + Priase + '\'' +
                ", imgCollectionFirst='" + imgCollectionFirst + '\'' +
                ", imgCollection='" + imgCollection + '\'' +
                ", isClicked='" + isClicked + '\'' +
                ", usernameSet='" + usernameSet + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserName);
        dest.writeString(Photo);
        dest.writeString(AcBanner);
        dest.writeString(LossLogId);
        dest.writeString(CreateDate);
        dest.writeString(LogTitle);
        dest.writeString(LogContent);
        dest.writeString(Priase);
        dest.writeString(imgCollectionFirst);
        dest.writeString(imgCollection);
        dest.writeString(isClicked);
        dest.writeString(usernameSet);
        dest.writeString(AfterWeight);
    }
}
