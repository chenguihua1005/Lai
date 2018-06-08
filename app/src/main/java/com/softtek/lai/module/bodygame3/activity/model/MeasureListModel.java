package com.softtek.lai.module.bodygame3.activity.model;

import java.util.List;

/**
 * Created by Terry on 2016/11/29.
 */

public class MeasureListModel {
    private String status;
    private String count;
    private List<MeasureModel> MemberList;


    @Override
    public String toString() {
        return "MeasureListModel{" +
                "status='" + status + '\'' +
                ", count='" + count + '\'' +
                ", MemberList=" + MemberList +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<MeasureModel> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<MeasureModel> memberList) {
        MemberList = memberList;
    }
}
