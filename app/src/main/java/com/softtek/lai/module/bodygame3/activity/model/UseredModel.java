package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by shelly.xu on 11/28/2016.
 * 已报名的小伙伴
 */

public class UseredModel {
    private int UserId;
    private String UserIcon;
    private String UserName;

    public UseredModel(int userId, String userIcon, String userName) {
        UserId = userId;
        UserIcon = userIcon;
        UserName = userName;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserIcon() {
        return UserIcon;
    }

    public void setUserIcon(String userIcon) {
        UserIcon = userIcon;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
