package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by jessica.zhang on 3/9/2017.
 */

public class FuceClassAlbumModel {
    private String ClassName;//班级名称
    private String JoinDate;//参赛日期
    private List<FucePhotoModel> PhotoList;

    public FuceClassAlbumModel(String className, String joinDate, List<FucePhotoModel> photoList) {
        ClassName = className;
        JoinDate = joinDate;
        PhotoList = photoList;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(String joinDate) {
        JoinDate = joinDate;
    }

    public List<FucePhotoModel> getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(List<FucePhotoModel> photoList) {
        PhotoList = photoList;
    }
}
