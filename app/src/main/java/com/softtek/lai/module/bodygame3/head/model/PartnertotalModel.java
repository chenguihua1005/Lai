package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class PartnertotalModel {
    private String TotalPage;
    private List<PartnersModel> PartnersList;

    public PartnertotalModel(String totalPage, List<PartnersModel> partnersList) {
        TotalPage = totalPage;
        PartnersList = partnersList;
    }

    public String getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<PartnersModel> getPartnersList() {
        return PartnersList;
    }

    public void setPartnersList(List<PartnersModel> partnersList) {
        PartnersList = partnersList;
    }
}
