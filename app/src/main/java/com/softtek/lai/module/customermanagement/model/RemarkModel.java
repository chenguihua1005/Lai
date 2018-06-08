package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jessica.zhang on 12/11/2017.
 */

public class RemarkModel {
    private int PageCount;
    private List<RemarkItemModel> Items;

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public List<RemarkItemModel> getItems() {
        return Items;
    }

    public void setItems(List<RemarkItemModel> items) {
        Items = items;
    }

    @Override
    public String toString() {
        return "RemarkModel{" +
                "PageCount=" + PageCount +
                ", Items=" + Items +
                '}';
    }
}
