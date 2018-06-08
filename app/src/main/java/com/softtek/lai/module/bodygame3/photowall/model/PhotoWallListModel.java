package com.softtek.lai.module.bodygame3.photowall.model;

import java.util.List;

/**
 * Created by lareina.qiao on 12/3/2016.
 */
public class PhotoWallListModel {
    private String TotalPage;
    private List<PhotoWallslistModel> PhotoWallslist;

    @Override
    public String toString() {
        return "PhotoWallListModel{" +
                "TotalPage='" + TotalPage + '\'' +
                ", PhotoWallslist=" + PhotoWallslist +
                '}';
    }

    public String getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<PhotoWallslistModel> getPhotoWallslist() {
        return PhotoWallslist;
    }

    public void setPhotoWallslist(List<PhotoWallslistModel> photoWallslist) {
        PhotoWallslist = photoWallslist;
    }
}
