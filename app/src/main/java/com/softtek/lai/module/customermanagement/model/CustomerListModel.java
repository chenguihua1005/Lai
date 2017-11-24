package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jessica.zhang on 11/23/2017.
 */

public class CustomerListModel {
    private int PageCount;//总页数
    private List<CustomerModel> Items;//客户列表

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public List<CustomerModel> getItems() {
        return Items;
    }

    public void setItems(List<CustomerModel> items) {
        Items = items;
    }

    @Override
    public String toString() {
        return "CustomerListModel{" +
                "PageCount=" + PageCount +
                ", Items=" + Items +
                '}';
    }
}
