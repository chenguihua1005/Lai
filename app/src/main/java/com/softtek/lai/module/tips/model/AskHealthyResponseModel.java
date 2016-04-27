package com.softtek.lai.module.tips.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/27/2016.
 */
public class AskHealthyResponseModel {


    private String TotalPage;
    private List<AskHealthyModel> TipsList;

    public String getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<AskHealthyModel> getTipsList() {
        return TipsList;
    }

    public void setTipsList(List<AskHealthyModel> tipsList) {
        TipsList = tipsList;
    }
}
