package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by curry.zhang on 12/12/2016.
 */

import java.util.List;

/**
 * 小组排名页面总实体类
 */
public class HonorGroupRankModel {
    private String StartDate;
    private String EndDate;
    private List<ListGroupRankingModel> grouplist;

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public List<ListGroupRankingModel> getGrouplist() {
        return grouplist;
    }

    public void setGrouplist(List<ListGroupRankingModel> grouplist) {
        this.grouplist = grouplist;
    }

    @Override
    public String toString() {   
        return "HonorGroupRankModel{" +
                "StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", grouplist=" + grouplist +
                '}';
    }
}
