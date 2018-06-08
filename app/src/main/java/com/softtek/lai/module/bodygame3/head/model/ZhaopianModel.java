package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class ZhaopianModel {
//    "UserPhoto": "",
//            "UserName": "",
//            "Num": "",					————评论数
//    "ReleaseTime": "",
//            "PhotoNameList": ["123456.img","123456.img"],
//            "PhotoThumbnailList": ["123456.img"]		————缩略图list
    private String UserPhoto;
    private String UserName;
    private String Num;
    private String ReleaseTime;
    private List<String> PhotoNameList;
    private List<String> PhotoThumbnailList;

    public ZhaopianModel(String userPhoto, String userName, String num, String releaseTime, List<String> photoNameList, List<String> photoThumbnailList) {
        UserPhoto = userPhoto;
        UserName = userName;
        Num = num;
        ReleaseTime = releaseTime;
        PhotoNameList = photoNameList;
        PhotoThumbnailList = photoThumbnailList;
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getReleaseTime() {
        return ReleaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        ReleaseTime = releaseTime;
    }

    public List<String> getPhotoNameList() {
        return PhotoNameList;
    }

    public void setPhotoNameList(List<String> photoNameList) {
        PhotoNameList = photoNameList;
    }

    public List<String> getPhotoThumbnailList() {
        return PhotoThumbnailList;
    }

    public void setPhotoThumbnailList(List<String> photoThumbnailList) {
        PhotoThumbnailList = photoThumbnailList;
    }

    @Override
    public String toString() {
        return "ZhaopianModel{" +
                "UserPhoto='" + UserPhoto + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Num='" + Num + '\'' +
                ", ReleaseTime='" + ReleaseTime + '\'' +
                ", PhotoNameList=" + PhotoNameList +
                ", PhotoThumbnailList=" + PhotoThumbnailList +
                '}';
    }
}
