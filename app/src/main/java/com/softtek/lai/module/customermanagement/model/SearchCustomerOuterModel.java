package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jessica.zhang on 11/29/2017.
 */

public class SearchCustomerOuterModel {
    private List<CustomerModel> Items;
    private int PageCount;

    public List<CustomerModel> getItems() {
        return Items;
    }

    public void setItems(List<CustomerModel> items) {
        Items = items;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    @Override
    public String toString() {
        return "SearchCustomerOuterModel{" +
                "Items=" + Items +
                ", PageCount=" + PageCount +
                '}';
    }
}
