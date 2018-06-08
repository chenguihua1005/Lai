package com.softtek.lai.module.bodygame3.activity.model;

import java.util.List;

/**
 * Created by shelly.xu on 11/28/2016.
 */

public class ActdetailModel {
    private String Title;
    private String StartTime;
    private String Content;
    private Boolean IsSign;//是否报名
    private Boolean IsEnd;//是否结束
    private List<UseredModel> Users;

    public ActdetailModel(String title, String startTime, String content, Boolean isSign, Boolean isEnd, List<UseredModel> users) {
        Title = title;
        StartTime = startTime;
        Content = content;
        IsSign = isSign;
        IsEnd = isEnd;
        Users = users;
    }

    public Boolean getEnd() {
        return IsEnd;
    }

    public void setEnd(Boolean end) {
        IsEnd = end;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Boolean getSign() {
        return IsSign;
    }

    public void setSign(Boolean sign) {
        IsSign = sign;
    }

    public List<UseredModel> getUsers() {
        return Users;
    }

    public void setUsers(List<UseredModel> users) {
        Users = users;
    }
}
