package com.softtek.lai.module.bodygame3.activity.model;

import java.util.List;

/**
 * Created by Terry on 2016/11/29.
 */

public class AuditListModel {
    private int status;
    private String count;
    private List<MemberListModel>  MemberList;

    @Override
    public String toString() {
        return "AuditListModel{" +
                "status='" + status + '\'' +
                ", count='" + count + '\'' +
                ", MemberList=" + MemberList +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<MemberListModel> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<MemberListModel> memberList) {
        MemberList = memberList;
    }
}
