package com.softtek.lai.module.community.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/19/2016.
 */
public class HealthyRecommendModel {

    private  String TotalPage;
    private  List<HealthyCommunityModel> HealthList;

    public String getTotalPage() {
        return TotalPage==null||"".equals(TotalPage)?"0":TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<HealthyCommunityModel> getHealthList() {
        return HealthList;
    }

    public void setHealthList(List<HealthyCommunityModel> healthList) {
        HealthList = healthList;
    }

    @Override
    public String toString() {
        return "HealthyRecommendModel{" +
                "TotalPage='" + TotalPage + '\'' +
                ", HealthList=" + HealthList +
                '}';
    }
}
