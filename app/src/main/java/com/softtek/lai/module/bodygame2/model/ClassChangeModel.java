package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class ClassChangeModel {
    private ClassDetailModel ClassDetail;
    private DyNoticeModel DyNotice;
    private DySysModel DySys;
    private ClmListModel Clmlist;

    public ClassChangeModel(ClassDetailModel classDetail, DyNoticeModel dyNotice, DySysModel dySys, ClmListModel clmlist) {
        ClassDetail = classDetail;
        DyNotice = dyNotice;
        DySys = dySys;
        Clmlist = clmlist;
    }

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

    public ClmListModel getClmlist() {
        return Clmlist;
    }

    public void setClmlist(ClmListModel clmlist) {
        Clmlist = clmlist;
    }
}
