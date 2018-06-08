package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by 87356 on 2016/12/3.
 */
public class HonorRankModel {
    private String TotalLoss;//本班总减重/脂
    private String AvgLoss;//本班人均减重/脂

    private List<ClassModel> list_class;//班级列表

    private List<ListTopModel> list_top3;
    private List<ListdateModel> list_date;

    private List<ListGroupModel> list_group;//ListGroupModel
    private List<ListGroupModel> list_all;//班级排名

    public List<ClassModel> getList_class() {
        return list_class;
    }

    public void setList_class(List<ClassModel> list_class) {
        this.list_class = list_class;
    }

    public List<ListGroupModel> getList_all() {
        return list_all;
    }

    public void setList_all(List<ListGroupModel> list_all) {
        this.list_all = list_all;
    }

    public String getTotalLoss() {
        return TotalLoss;
    }

    public void setTotalLoss(String totalLoss) {
        TotalLoss = totalLoss;
    }

    public String getAvgLoss() {
        return AvgLoss;
    }

    public void setAvgLoss(String avgLoss) {
        AvgLoss = avgLoss;
    }

    public List<ListTopModel> getList_top3() {
        return list_top3;
    }

    public void setList_top3(List<ListTopModel> list_top3) {
        this.list_top3 = list_top3;
    }

    public List<ListGroupModel> getList_group() {
        return list_group;
    }

    public void setList_group(List<ListGroupModel> list_group) {
        this.list_group = list_group;
    }

    public List<ListdateModel> getList_date() {
        return list_date;
    }

    public void setList_date(List<ListdateModel> list_date) {
        this.list_date = list_date;
    }
}
