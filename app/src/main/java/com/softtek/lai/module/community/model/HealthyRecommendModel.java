package com.softtek.lai.module.community.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/19/2016.
 */
public class HealthyRecommendModel {

    private  String TotalPage;
    private  List<DynamicModel> Dynamiclist;

    public String getTotalPage() {
        return TotalPage==null||"".equals(TotalPage)?"0":TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<DynamicModel> getDynamiclist() {
        return Dynamiclist;
    }

    public void setDynamiclist(List<DynamicModel> dynamiclist) {
        Dynamiclist = dynamiclist;
    }

    @Override
    public String toString() {
        return "HealthyRecommendModel{" +
                "TotalPage='" + TotalPage + '\'' +
                ", HealthList=" + Dynamiclist +
                '}';
    }
}
