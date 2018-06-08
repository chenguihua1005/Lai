package com.softtek.lai.module.community.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 2016/4/19.
 * 健康圈动态请求数据接收模型
 */
public class HealthyDynamicModel implements Parcelable{

    private String HealtId;
    private String Content;
    private String CreateDate;
    private String UserName;
    private String Photo;
    private String imgCollection;
    private String IsPraise;
    private String PraiseNum;
    private String usernameSet;

    public HealthyDynamicModel(){

    }

    protected HealthyDynamicModel(Parcel in) {
        HealtId = in.readString();
        Content = in.readString();
        CreateDate = in.readString();
        UserName = in.readString();
        Photo = in.readString();
        imgCollection = in.readString();
        IsPraise = in.readString();
        PraiseNum = in.readString();
        usernameSet = in.readString();
    }

    public static final Creator<HealthyDynamicModel> CREATOR = new Creator<HealthyDynamicModel>() {
        @Override
        public HealthyDynamicModel createFromParcel(Parcel in) {
            return new HealthyDynamicModel(in);
        }

        @Override
        public HealthyDynamicModel[] newArray(int size) {
            return new HealthyDynamicModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HealtId);
        dest.writeString(Content);
        dest.writeString(CreateDate);
        dest.writeString(UserName);
        dest.writeString(Photo);
        dest.writeString(imgCollection);
        dest.writeString(IsPraise);
        dest.writeString(PraiseNum);
        dest.writeString(usernameSet);
    }
}
