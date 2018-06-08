package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by jessica.zhang on 3/9/2017.
 */

public class FuceAlbumModel {
    private int PageIndex;
    private int TotalPages;
    private List<FuceClassAlbumModel> Photos;

    public FuceAlbumModel(int pageIndex, int totalPages, List<FuceClassAlbumModel> photos) {
        PageIndex = pageIndex;
        TotalPages = totalPages;
        Photos = photos;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }

    public List<FuceClassAlbumModel> getPhotos() {
        return Photos;
    }

    public void setPhotos(List<FuceClassAlbumModel> photos) {
        Photos = photos;
    }
}
