package com.softtek.lai.module.bodygame2.model;

import android.widget.ListView;

import java.util.List;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class ClassChangeModel {
    private ClassDetailModel ClassDetail;
    private DyNoticeModel DyNotice;
    private DySysModel DySys;
    private List<ClmListModel> Clmlist;


    public ClassDetailModel getClassDetail() {
        return ClassDetail;
    }

    public void setClassDetail(ClassDetailModel classDetail) {
        ClassDetail = classDetail;
    }

    public DyNoticeModel getDyNotice() {
        return DyNotice;
    }

    public void setDyNotice(DyNoticeModel dyNotice) {
        DyNotice = dyNotice;
    }

    public DySysModel getDySys() {
        return DySys;
    }

    public void setDySys(DySysModel dySys) {
        DySys = dySys;
    }

    @Override
    public String toString() {
        return "ClassChangeModel{" +
                "ClassDetail=" + ClassDetail +
                ", DyNotice=" + DyNotice +
                ", DySys=" + DySys +
                ", Clmlist=" + Clmlist +
                '}';
    }

    public List<ClmListModel> getClmlist() {
        return Clmlist;
    }

    public void setClmlist(List<ClmListModel> clmlist) {
        Clmlist = clmlist;
    }
}
