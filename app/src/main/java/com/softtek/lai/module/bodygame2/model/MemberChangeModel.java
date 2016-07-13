package com.softtek.lai.module.bodygame2.model;

import java.util.List;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class MemberChangeModel {
    private List<ClmListModel> Clmlist;

    public List<ClmListModel> getClmlist() {
        return Clmlist;
    }

    public void setClmlist(List<ClmListModel> clmlist) {
        Clmlist = clmlist;
    }

    @Override
    public String toString() {
        return "MemberChangeModel{" +
                "Clmlist=" + Clmlist +
                '}';
    }
}
