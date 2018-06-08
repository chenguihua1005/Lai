package com.softtek.lai.module.sport2.model;

/**
 * @author jerry.Guan
 * @date 2016/10/22
 */

public class Unread {

    private String unreadCount;

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

    @Override
    public String toString() {
        return "Unread{" +
                "unreadCount='" + unreadCount + '\'' +
                '}';
    }
}
