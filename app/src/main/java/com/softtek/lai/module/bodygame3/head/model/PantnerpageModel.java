package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by Lenovo-G400 on 2016/12/1.
 */

public class PantnerpageModel {
    private String TotalPage;
    private List<PartnerlistModel> PartnersList;

    public PantnerpageModel(String totalPage, List<PartnerlistModel> partnersList) {
        TotalPage = totalPage;
        PartnersList = partnersList;
    }

    public String getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<PartnerlistModel> getPartnersList() {
        return PartnersList;
    }

    public void setPartnersList(List<PartnerlistModel> partnersList) {
        PartnersList = partnersList;
    }
}
