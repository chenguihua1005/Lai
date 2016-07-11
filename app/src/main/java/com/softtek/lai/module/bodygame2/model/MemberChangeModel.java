package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class MemberChangeModel {
private ClmListModel Clmlist;

    public MemberChangeModel(ClmListModel clmlist) {
        Clmlist = clmlist;
    }

    public ClmListModel getClmlist() {
        return Clmlist;
    }

    public void setClmlist(ClmListModel clmlist) {
        Clmlist = clmlist;
    }
}
