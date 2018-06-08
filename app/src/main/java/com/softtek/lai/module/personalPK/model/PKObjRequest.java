package com.softtek.lai.module.personalPK.model;

import java.util.List;

/**
 * Created by jerry.guan on 5/20/2016.
 */
public class PKObjRequest {

    private int PageCount;
    private List<PKObjModel> data;

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public List<PKObjModel> getData() {
        return data;
    }

    public void setData(List<PKObjModel> data) {
        this.data = data;
    }
}
