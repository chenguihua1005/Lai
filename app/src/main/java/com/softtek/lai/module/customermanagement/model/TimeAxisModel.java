package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class TimeAxisModel {
    private int PageCount;
    private List<TimeAxisItemModel> Items;

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public List<TimeAxisItemModel> getItems() {
        return Items;
    }

    public void setItems(List<TimeAxisItemModel> items) {
        Items = items;
    }
}
