package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by lareina.qiao on 2/24/2017.
 */

public class SearchFriendModel {
    private int pageIndex;
    private int totalPages;
    private List<UserListModel>userList;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserListModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListModel> userList) {
        this.userList = userList;
    }
}
