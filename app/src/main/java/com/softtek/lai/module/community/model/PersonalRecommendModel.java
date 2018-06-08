package com.softtek.lai.module.community.model;

import java.util.List;

/**
 * Created by jerry.guan on 10/9/2016.
 */
public class PersonalRecommendModel {

    private  int TotalPage;
    private String UserName;
    private String Photo;
    private int IsFocus;
    private int HealthCount;

    private List<PersonalListModel> HealthList;

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int totalPage) {
        TotalPage = totalPage;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public int getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(int isFocus) {
        IsFocus = isFocus;
    }

    public int getHealthCount() {
        return HealthCount;
    }

    public void setHealthCount(int healthCount) {
        HealthCount = healthCount;
    }

    public List<PersonalListModel> getHealthList() {
        return HealthList;
    }

    public void setHealthList(List<PersonalListModel> healthList) {
        HealthList = healthList;
    }

    @Override
    public String toString() {
        return "PersonalRecommendModel{" +
                "TotalPage=" + TotalPage +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", IsFocus=" + IsFocus +
                ", HealthCount=" + HealthCount +
                ", HealthList=" + HealthList +
                '}';
    }
}
