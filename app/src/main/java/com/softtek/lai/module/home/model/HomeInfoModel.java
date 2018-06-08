/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public class HomeInfoModel implements Serializable ,Parcelable{

    private String Img_Type;//放置位置

    private String Img_Title;//图片标题

    private String Img_Order;//显示顺序

    private String Img_Addr;//图片路径

    private String BannerType;
    private String ArtUrl;//文章链接

    protected HomeInfoModel(Parcel in) {
        Img_Type = in.readString();
        Img_Title = in.readString();
        Img_Order = in.readString();
        Img_Addr = in.readString();
        BannerType = in.readString();
        ArtUrl = in.readString();
    }

    public static final Creator<HomeInfoModel> CREATOR = new Creator<HomeInfoModel>() {
        @Override
        public HomeInfoModel createFromParcel(Parcel in) {
            return new HomeInfoModel(in);
        }

        @Override
        public HomeInfoModel[] newArray(int size) {
            return new HomeInfoModel[size];
        }
    };

    public String getArtUrl() {
        return ArtUrl;
    }

    public void setArtUrl(String artUrl) {
        ArtUrl = artUrl;
    }

    public String getBannerType() {
        return BannerType;
    }

    public void setBannerType(String bannerType) {
        BannerType = bannerType;
    }

    public String getImg_Type() {
        return Img_Type;
    }

    public void setImg_Type(String img_Type) {
        Img_Type = img_Type;
    }

    public String getImg_Title() {
        return Img_Title;
    }

    public void setImg_Title(String img_Title) {
        Img_Title = img_Title;
    }

    public String getImg_Order() {
        return Img_Order;
    }

    public void setImg_Order(String img_Order) {
        Img_Order = img_Order;
    }

    public String getImg_Addr() {
        return Img_Addr;
    }

    public void setImg_Addr(String img_Addr) {
        Img_Addr = img_Addr;
    }


    @Override
    public String toString() {
        return "HomeInfoModel{" +
                "Img_Type='" + Img_Type + '\'' +
                ", Img_Title='" + Img_Title + '\'' +
                ", Img_Order='" + Img_Order + '\'' +
                ", Img_Addr='" + Img_Addr + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Img_Type);
        dest.writeString(Img_Title);
        dest.writeString(Img_Order);
        dest.writeString(Img_Addr);
        dest.writeString(BannerType);
        dest.writeString(ArtUrl);
    }
}
