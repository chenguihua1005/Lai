package com.softtek.lai.module.pastreview.model;

import java.util.List;

/**
 * Created by lareina.qiao on 6/28/2016.
 */
public class MyPhotoListModel {
    private String PageCount;
    private List<PhotoListModel> PhotoList;

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }

    public List<PhotoListModel> getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(List<PhotoListModel> photoList) {
        PhotoList = photoList;
    }
}
