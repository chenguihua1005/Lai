package com.softtek.lai.module.bodygame2.model;

import java.util.List;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class ClassMainModel {
    private List<ClassListModel> Classlist;
    private ClassDetailModel ClassDetail;
    private DyNoticeModel DyNotice;
    private DySysModel DySys;
    private List<ClmListModel> Clmlist;

    public ClassMainModel(List<ClassListModel> classlist, ClassDetailModel classDetail, DyNoticeModel dyNotice, DySysModel dySys, List<ClmListModel> clmlist) {
        Classlist = classlist;
        ClassDetail = classDetail;
        DyNotice = dyNotice;
        DySys = dySys;
        Clmlist = clmlist;
    }

    public List<ClassListModel> getClasslist() {
        return Classlist;
    }

    public void setClasslist(List<ClassListModel> classlist) {
        Classlist = classlist;
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

    public List<ClmListModel> getClmlist() {
        return Clmlist;
    }

    public void setClmlist(List<ClmListModel> clmlist) {
        Clmlist = clmlist;
    }
}
