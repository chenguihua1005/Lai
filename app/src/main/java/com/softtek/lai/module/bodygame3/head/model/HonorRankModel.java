package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by 87356 on 2016/12/3.
 */
public class HonorRankModel {
    private List<ListTopModel> list_top3;
    private List<ListGroupModel>list_group;
    private List<ListdateModel>list_date;

    @Override
    public String toString() {
        return "HonorRankModel{" +
                "list_top3=" + list_top3 +
                ", list_group=" + list_group +
                ", list_date=" + list_date +
                '}';
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
