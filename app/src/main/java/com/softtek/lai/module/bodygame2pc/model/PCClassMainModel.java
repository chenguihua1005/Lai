package com.softtek.lai.module.bodygame2pc.model;

import com.softtek.lai.module.bodygame2.model.ClassDetailModel;
import com.softtek.lai.module.bodygame2.model.ClassListModel;
import com.softtek.lai.module.bodygame2.model.ClmListModel;
import com.softtek.lai.module.bodygame2.model.DyNoticeModel;
import com.softtek.lai.module.bodygame2.model.DySysModel;

import java.util.List;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class PCClassMainModel {
    private PCClmDetailModel ClmDetail;
    private DyNoticeModel DyNotice;
    private DySysModel DySys;
    private List<ClmListModel> Clmlist;

    @Override
    public String toString() {
        return "PCClassMainModel{" +
                "ClmDetail=" + ClmDetail +
                ", DyNotice=" + DyNotice +
                ", DySys=" + DySys +
                ", Clmlist=" + Clmlist +
                '}';
    }

    public PCClmDetailModel getClmDetail() {
        return ClmDetail;
    }

    public void setClmDetail(PCClmDetailModel clmDetail) {
        ClmDetail = clmDetail;
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
